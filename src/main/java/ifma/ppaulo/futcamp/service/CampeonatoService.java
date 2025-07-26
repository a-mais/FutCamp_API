package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.CampeonatoDTO;
import ifma.ppaulo.futcamp.dto.ClassificacaoDTO;
import ifma.ppaulo.futcamp.model.Campeonato;
import ifma.ppaulo.futcamp.model.Partida;
import ifma.ppaulo.futcamp.model.Time;
import ifma.ppaulo.futcamp.repository.CampeonatoRepository;
import ifma.ppaulo.futcamp.repository.PartidaRepository;
import ifma.ppaulo.futcamp.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final PartidaRepository partidaRepository;
    private final TimeRepository timeRepository;

    @Transactional
    public CampeonatoDTO criar(CampeonatoDTO campeonatoDTO) {
        if (campeonatoDTO.getAno() < 1900 || campeonatoDTO.getAno() > 2100) {
            throw new IllegalArgumentException("Ano inválido para o campeonato");
        }

        if (campeonatoRepository.existsByNomeAndAno(campeonatoDTO.getNome(), campeonatoDTO.getAno())) {
            throw new IllegalArgumentException("Já existe um campeonato com este nome no ano especificado");
        }

        Campeonato campeonato = convertToEntity(campeonatoDTO);
        campeonato = campeonatoRepository.save(campeonato);
        return convertToDTO(campeonato);
    }

    public List<CampeonatoDTO> listarTodos() {
        return campeonatoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CampeonatoDTO buscarPorId(Integer id) {
        return campeonatoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));
    }

    @Transactional
    public CampeonatoDTO atualizar(Integer id, CampeonatoDTO campeonatoDTO) {
        Campeonato campeonatoExistente = campeonatoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        if (!campeonatoExistente.getNome().equals(campeonatoDTO.getNome()) &&
            campeonatoRepository.existsByNomeAndAno(campeonatoDTO.getNome(), campeonatoDTO.getAno())) {
            throw new IllegalArgumentException("Já existe um campeonato com este nome no ano especificado");
        }

        campeonatoExistente.setNome(campeonatoDTO.getNome());
        campeonatoExistente.setAno(campeonatoDTO.getAno());

        campeonatoExistente = campeonatoRepository.save(campeonatoExistente);
        return convertToDTO(campeonatoExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        Campeonato campeonato = campeonatoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        if (!campeonato.getPartidas().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um campeonato que possui partidas registradas");
        }

        campeonatoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ClassificacaoDTO> getTabela(Integer campeonatoId) {
        Campeonato campeonato = campeonatoRepository.findByIdWithTimes(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        Set<Time> times = campeonato.getTimes();
        if (times.isEmpty()) {
            throw new IllegalStateException("O campeonato não possui times cadastrados");
        }

        List<Partida> partidas = partidaRepository.findByCampeonatoIdWithTimes(campeonatoId);
        Map<Time, ClassificacaoDTO> tabelaMap = new HashMap<>();

        // Inicializa a classificação para cada time
        times.forEach(time -> {
            tabelaMap.put(time, new ClassificacaoDTO(
                time.getId(),
                time.getNome(),
                0,0, 0, 0, 0, 0, 0, 0
            ));
        });

        // Processa cada partida com resultado
        partidas.stream()
                .filter(partida -> partida.getResultado() != null)
                .forEach(partida -> processarPartida(partida, tabelaMap));

        // Retorna a tabela ordenada
        return tabelaMap.values().stream()
                .sorted((a, b) -> {
                    if (a.getPontos().equals(b.getPontos())) {
                        int saldoA = a.getGolsPro() - a.getGolsContra();
                        int saldoB = b.getGolsPro() - b.getGolsContra();
                        if (saldoA == saldoB) {
                            return b.getGolsPro().compareTo(a.getGolsPro());
                        }
                        return Integer.compare(saldoB, saldoA);
                    }
                    return b.getPontos().compareTo(a.getPontos());
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void adicionarTime(Integer campeonatoId, Integer timeId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        if (campeonato.getTimes().contains(time)) {
            throw new IllegalStateException("Time já está participando deste campeonato");
        }

        campeonato.getTimes().add(time);
        campeonatoRepository.save(campeonato);
    }

    private void processarPartida(Partida partida, Map<Time, ClassificacaoDTO> tabelaMap) {
        ClassificacaoDTO classifMandante = tabelaMap.get(partida.getTimeMandante());
        ClassificacaoDTO classifVisitante = tabelaMap.get(partida.getTimeVisitante());

        if (classifMandante == null || classifVisitante == null) {
            throw new IllegalStateException("Time não encontrado na tabela de classificação");
        }

        int golsMandante = partida.getResultado().getGolsMandante();
        int golsVisitante = partida.getResultado().getGolsVisitante();

        // Atualiza jogos e gols
        atualizarEstatisticas(classifMandante, golsMandante, golsVisitante);
        atualizarEstatisticas(classifVisitante, golsVisitante, golsMandante);

        // Atualiza pontos
        if (golsMandante > golsVisitante) {
            atualizarPontuacao(classifMandante, classifVisitante, true);
        } else if (golsVisitante > golsMandante) {
            atualizarPontuacao(classifVisitante, classifMandante, true);
        } else {
            atualizarPontuacao(classifMandante, classifVisitante, false);
        }
    }

    private void atualizarEstatisticas(ClassificacaoDTO classif, int golsPro, int golsContra) {
        classif.setJogos(classif.getJogos() + 1);
        classif.setGolsPro(classif.getGolsPro() + golsPro);
        classif.setGolsContra(classif.getGolsContra() + golsContra);
    }

    private void atualizarPontuacao(ClassificacaoDTO vencedor, ClassificacaoDTO perdedor, boolean vitoria) {
        if (vitoria) {
            vencedor.setVitorias(vencedor.getVitorias() + 1);
            vencedor.setPontos(vencedor.getPontos() + 3);
            perdedor.setDerrotas(perdedor.getDerrotas() + 1);
        } else {
            vencedor.setEmpates(vencedor.getEmpates() + 1);
            vencedor.setPontos(vencedor.getPontos() + 1);
            perdedor.setEmpates(perdedor.getEmpates() + 1);
            perdedor.setPontos(perdedor.getPontos() + 1);
        }
    }

    private Campeonato convertToEntity(CampeonatoDTO dto) {
        return Campeonato.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .ano(dto.getAno())
                .times(new HashSet<>())
                .partidas(new HashSet<>())
                .build();
    }

    private CampeonatoDTO convertToDTO(Campeonato campeonato) {
        return CampeonatoDTO.builder()
                .id(campeonato.getId())
                .nome(campeonato.getNome())
                .ano(campeonato.getAno())
                .build();
    }
}

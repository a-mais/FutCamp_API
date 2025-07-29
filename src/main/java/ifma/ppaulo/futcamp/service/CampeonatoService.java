package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.CampeonatoDTO;
import ifma.ppaulo.futcamp.dto.ClassificacaoDTO;
import ifma.ppaulo.futcamp.dto.TimeDTO;
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
    private final TabelaService tabelaService;

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
    public TimeDTO adicionarTimeCampeonato(Integer campeonatoId, Integer timeId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        if (campeonato.getTimes().contains(time)) {
            throw new IllegalStateException("Time já está participando deste campeonato");
        }

        // Adiciona o time ao campeonato
        campeonato.addTime(time);
        campeonatoRepository.save(campeonato);

        // Inicializa a posição do time na tabela
        tabelaService.inicializarTimeNoCampeonato(timeId, campeonatoId);

        return convertTimeToDTO(time);
    }

    @Transactional(readOnly = true)
    public List<TimeDTO> listarTimesDoCampeonato(Integer campeonatoId) {
        Campeonato campeonato = campeonatoRepository.findByIdWithTimes(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        return campeonato.getTimes().stream()
                .map(this::convertTimeToDTO)
                .collect(Collectors.toList());
    }

    private void processarPartida(Partida partida, Map<Time, ClassificacaoDTO> tabelaMap) {
        ClassificacaoDTO classifMandante = tabelaMap.get(partida.getTimeMandante());
        ClassificacaoDTO classifVisitante = tabelaMap.get(partida.getTimeVisitante());

        if (classifMandante == null || classifVisitante == null) {
            throw new IllegalStateException("Time não encontrado na tabela de classificação");
        }

        int golsMandante = partida.getResultado().getGolsMandante();
        int golsVisitante = partida.getResultado().getGolsVisitante();

        // Atualiza jogos
        classifMandante.setJogos(classifMandante.getJogos() + 1);
        classifVisitante.setJogos(classifVisitante.getJogos() + 1);

        // Atualiza gols
        classifMandante.setGolsPro(classifMandante.getGolsPro() + golsMandante);
        classifMandante.setGolsContra(classifMandante.getGolsContra() + golsVisitante);
        classifVisitante.setGolsPro(classifVisitante.getGolsPro() + golsVisitante);
        classifVisitante.setGolsContra(classifVisitante.getGolsContra() + golsMandante);

        // Atualiza vitórias, empates, derrotas e pontos
        if (golsMandante > golsVisitante) {
            // Vitória do mandante
            classifMandante.setVitorias(classifMandante.getVitorias() + 1);
            classifMandante.setPontos(classifMandante.getPontos() + 3);
            classifVisitante.setDerrotas(classifVisitante.getDerrotas() + 1);
        } else if (golsMandante < golsVisitante) {
            // Vitória do visitante
            classifVisitante.setVitorias(classifVisitante.getVitorias() + 1);
            classifVisitante.setPontos(classifVisitante.getPontos() + 3);
            classifMandante.setDerrotas(classifMandante.getDerrotas() + 1);
        } else {
            // Empate
            classifMandante.setEmpates(classifMandante.getEmpates() + 1);
            classifVisitante.setEmpates(classifVisitante.getEmpates() + 1);
            classifMandante.setPontos(classifMandante.getPontos() + 1);
            classifVisitante.setPontos(classifVisitante.getPontos() + 1);
        }
    }

    private Campeonato convertToEntity(CampeonatoDTO dto) {
        return Campeonato.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .ano(dto.getAno())
                .build();
    }

    private CampeonatoDTO convertToDTO(Campeonato campeonato) {
        return CampeonatoDTO.builder()
                .id(campeonato.getId())
                .nome(campeonato.getNome())
                .ano(campeonato.getAno())
                .build();
    }

    private TimeDTO convertTimeToDTO(Time time) {
        return TimeDTO.builder()
                .id(time.getId())
                .nome(time.getNome())
                .cidade(time.getCidade())
                .build();
    }
}

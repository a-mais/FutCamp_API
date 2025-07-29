package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.TabelaDTO;
import ifma.ppaulo.futcamp.model.Campeonato;
import ifma.ppaulo.futcamp.model.Tabela;
import ifma.ppaulo.futcamp.model.Time;
import ifma.ppaulo.futcamp.repository.CampeonatoRepository;
import ifma.ppaulo.futcamp.repository.TabelaRepository;
import ifma.ppaulo.futcamp.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TabelaService {
    private final TabelaRepository tabelaRepository;
    private final TimeRepository timeRepository;
    private final CampeonatoRepository campeonatoRepository;

    @Transactional(readOnly = true)
    public List<TabelaDTO> getClassificacao(Integer campeonatoId) {
        return tabelaRepository.findByCampeonatoIdOrderByPontosAndVitoriasAndSaldoGols(campeonatoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TabelaDTO getClassificacaoTime(Integer timeId, Integer campeonatoId) {
        return tabelaRepository.findByTimeIdAndCampeonatoId(timeId, campeonatoId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado na classificação deste campeonato"));
    }

    @Transactional(readOnly = true)
    public List<TabelaDTO> getClassificacaoTimeEmTodosCampeonatos(Integer timeId) {
        return tabelaRepository.findByTimeId(timeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TabelaDTO inicializarTimeNoCampeonato(Integer timeId, Integer campeonatoId) {
        Time time = timeRepository.findById(timeId)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        if (tabelaRepository.findByTimeIdAndCampeonatoId(timeId, campeonatoId).isPresent()) {
            throw new IllegalStateException("Time já está na tabela deste campeonato");
        }

        Tabela tabela = Tabela.builder()
                .time(time)
                .campeonato(campeonato)
                .pontos(0)
                .jogos(0)
                .vitorias(0)
                .empates(0)
                .derrotas(0)
                .golsPro(0)
                .golsContra(0)
                .saldoGols(0)
                .build();

        return convertToDTO(tabelaRepository.save(tabela));
    }

    private TabelaDTO convertToDTO(Tabela tabela) {
        return TabelaDTO.builder()
                .id(tabela.getId())
                .timeId(tabela.getTime().getId())
                .timeNome(tabela.getTime().getNome())
                .campeonatoId(tabela.getCampeonato().getId())
                .campeonatoNome(tabela.getCampeonato().getNome())
                .pontos(tabela.getPontos())
                .jogos(tabela.getJogos())
                .vitorias(tabela.getVitorias())
                .empates(tabela.getEmpates())
                .derrotas(tabela.getDerrotas())
                .golsPro(tabela.getGolsPro())
                .golsContra(tabela.getGolsContra())
                .saldoGols(tabela.getSaldoGols())
                .build();
    }
}

package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.PartidaDTO;
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

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final TimeRepository timeRepository;
    private final CampeonatoRepository campeonatoRepository;

    @Transactional
    public PartidaDTO criar(PartidaDTO partidaDTO) {
        validarPartida(partidaDTO);

        Partida partida = convertToEntity(partidaDTO);
        partida = partidaRepository.save(partida);
        return convertToDTO(partida);
    }

    public List<PartidaDTO> listarTodos() {
        return partidaRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PartidaDTO buscarPorId(Integer id) {
        return partidaRepository.findByIdWithRelationships(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Partida não encontrada"));
    }

    @Transactional
    public PartidaDTO atualizar(Integer id, PartidaDTO partidaDTO) {
        if (!partidaRepository.existsById(id)) {
            throw new EntityNotFoundException("Partida não encontrada");
        }

        validarPartida(partidaDTO);

        Partida partida = convertToEntity(partidaDTO);
        partida.setId(id);
        partida = partidaRepository.save(partida);
        return convertToDTO(partida);
    }

    @Transactional
    public void deletar(Integer id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partida não encontrada"));

        if (partida.getResultado() != null) {
            throw new IllegalStateException("Não é possível excluir uma partida que já possui resultado registrado");
        }

        partidaRepository.deleteById(id);
    }

    public List<PartidaDTO> buscarPorCampeonato(Integer campeonatoId) {
        return partidaRepository.findByCampeonatoIdWithTimes(campeonatoId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PartidaDTO> listarPartidasOcorridas(Integer campeonatoId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        return partidaRepository.findByCampeonatoAndResultadoIsNotNull(campeonato)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PartidaDTO> listarPartidasNaoOcorridas(Integer campeonatoId) {
        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        return partidaRepository.findByCampeonatoAndResultadoIsNull(campeonato)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validarPartida(PartidaDTO partidaDTO) {
        if (partidaDTO.getData().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data da partida não pode ser anterior à data atual");
        }

        Time timeMandante = timeRepository.findById(partidaDTO.getTimeMandanteId())
                .orElseThrow(() -> new EntityNotFoundException("Time mandante não encontrado"));

        Time timeVisitante = timeRepository.findById(partidaDTO.getTimeVisitanteId())
                .orElseThrow(() -> new EntityNotFoundException("Time visitante não encontrado"));

        if (timeMandante.getId().equals(timeVisitante.getId())) {
            throw new IllegalArgumentException("O time mandante não pode ser igual ao time visitante");
        }

        Campeonato campeonato = campeonatoRepository.findById(partidaDTO.getCampeonatoId())
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        if (!campeonato.getTimes().contains(timeMandante) || !campeonato.getTimes().contains(timeVisitante)) {
            throw new IllegalArgumentException("Um ou ambos os times não estão participando deste campeonato");
        }

        // Verifica se já existe uma partida entre estes times neste campeonato
        List<Partida> partidasExistentes = partidaRepository.findByCampeonatoId(campeonato.getId());
        boolean partidaDuplicada = partidasExistentes.stream()
                .anyMatch(p -> (p.getTimeMandante().getId().equals(timeMandante.getId()) &&
                              p.getTimeVisitante().getId().equals(timeVisitante.getId())) ||
                             (p.getTimeMandante().getId().equals(timeVisitante.getId()) &&
                              p.getTimeVisitante().getId().equals(timeMandante.getId())));

        if (partidaDuplicada) {
            throw new IllegalArgumentException("Já existe uma partida entre estes times neste campeonato");
        }
    }

    private Partida convertToEntity(PartidaDTO dto) {
        Time timeMandante = timeRepository.findById(dto.getTimeMandanteId())
                .orElseThrow(() -> new EntityNotFoundException("Time mandante não encontrado"));

        Time timeVisitante = timeRepository.findById(dto.getTimeVisitanteId())
                .orElseThrow(() -> new EntityNotFoundException("Time visitante não encontrado"));

        Campeonato campeonato = campeonatoRepository.findById(dto.getCampeonatoId())
                .orElseThrow(() -> new EntityNotFoundException("Campeonato não encontrado"));

        return Partida.builder()
                .id(dto.getId())
                .data(dto.getData())
                .timeMandante(timeMandante)
                .timeVisitante(timeVisitante)
                .campeonato(campeonato)
                .build();
    }

    private PartidaDTO convertToDTO(Partida partida) {
        return PartidaDTO.builder()
                .id(partida.getId())
                .data(partida.getData())
                .timeMandanteId(partida.getTimeMandante().getId())
                .timeVisitanteId(partida.getTimeVisitante().getId())
                .campeonatoId(partida.getCampeonato().getId())
                .build();
    }
}

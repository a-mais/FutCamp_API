package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.TimeDTO;
import ifma.ppaulo.futcamp.model.Time;
import ifma.ppaulo.futcamp.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeService {

    private final TimeRepository timeRepository;

    @Transactional
    public TimeDTO criar(TimeDTO timeDTO) {
        validarTime(timeDTO);

        Time time = convertToEntity(timeDTO);
        time = timeRepository.save(time);
        return convertToDTO(time);
    }

    public List<TimeDTO> listarTodos() {
        return timeRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TimeDTO buscarPorId(Integer id) {
        return timeRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));
    }

    @Transactional
    public TimeDTO atualizar(Integer id, TimeDTO timeDTO) {
        Time timeExistente = timeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        if (!timeExistente.getNome().equals(timeDTO.getNome()) &&
            timeRepository.existsByNome(timeDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um time com este nome");
        }

        timeExistente.setNome(timeDTO.getNome());
        timeExistente.setCidade(timeDTO.getCidade());
        timeExistente.setEstado(timeDTO.getEstado());

        timeExistente = timeRepository.save(timeExistente);
        return convertToDTO(timeExistente);
    }

    @Transactional(readOnly = true)
    public List<TimeDTO> buscarJogadoresDoTime(Integer id) {
        Time time = timeRepository.findByIdWithJogadores(id)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        return time.getJogadores().stream()
                .map(jogador -> TimeDTO.builder()
                        .id(time.getId())
                        .nome(jogador.getNome())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletar(Integer id) {
        Time time = timeRepository.findByIdWithJogadores(id)
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        if (!time.getJogadores().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um time que possui jogadores");
        }

        if (!time.getPartidasMandante().isEmpty() || !time.getPartidasVisitante().isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um time que possui partidas registradas");
        }

        timeRepository.deleteById(id);
    }

    private void validarTime(TimeDTO timeDTO) {
        if (timeDTO.getNome() == null || timeDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do time é obrigatório");
        }

        if (timeDTO.getCidade() == null || timeDTO.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("A cidade do time é obrigatória");
        }

        if (timeDTO.getEstado() == null || timeDTO.getEstado().trim().isEmpty() ||
            timeDTO.getEstado().length() != 2) {
            throw new IllegalArgumentException("O estado deve ser informado no formato UF (2 letras)");
        }

        if (timeRepository.existsByNome(timeDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um time com este nome");
        }
    }

    private Time convertToEntity(TimeDTO dto) {
        return Time.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cidade(dto.getCidade())
                .estado(dto.getEstado().toUpperCase())
                .jogadores(new HashSet<>())
                .partidasMandante(new HashSet<>())
                .partidasVisitante(new HashSet<>())
                .build();
    }

    private TimeDTO convertToDTO(Time time) {
        return TimeDTO.builder()
                .id(time.getId())
                .nome(time.getNome())
                .cidade(time.getCidade())
                .estado(time.getEstado())
                .build();
    }
}

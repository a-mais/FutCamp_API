package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.JogadorDTO;
import ifma.ppaulo.futcamp.model.Jogador;
import ifma.ppaulo.futcamp.model.Time;
import ifma.ppaulo.futcamp.repository.JogadorRepository;
import ifma.ppaulo.futcamp.repository.TimeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadorRepository;
    private final TimeRepository timeRepository;

    @Transactional
    public JogadorDTO criar(JogadorDTO jogadorDTO) {
        validarJogador(jogadorDTO);

        Jogador jogador = convertToEntity(jogadorDTO);
        jogador = jogadorRepository.save(jogador);
        return convertToDTO(jogador);
    }

    public List<JogadorDTO> listarTodos() {
        return jogadorRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public JogadorDTO buscarPorId(Integer id) {
        return jogadorRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Jogador não encontrado"));
    }

    @Transactional
    public JogadorDTO atualizar(Integer id, JogadorDTO jogadorDTO) {
        if (!jogadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Jogador não encontrado");
        }

        validarJogador(jogadorDTO);

        Jogador jogador = convertToEntity(jogadorDTO);
        jogador.setId(id);
        jogador = jogadorRepository.save(jogador);
        return convertToDTO(jogador);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!jogadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Jogador não encontrado");
        }
        jogadorRepository.deleteById(id);
    }

    public List<JogadorDTO> buscarPorTime(Integer timeId) {
        if (!timeRepository.existsById(timeId)) {
            throw new EntityNotFoundException("Time não encontrado");
        }
        return jogadorRepository.findByTimeId(timeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private void validarJogador(JogadorDTO jogadorDTO) {
        if (jogadorDTO.getNome() == null || jogadorDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do jogador é obrigatório");
        }

        if (jogadorDTO.getNumeroCamisa() <= 0 || jogadorDTO.getNumeroCamisa() > 99) {
            throw new IllegalArgumentException("O número da camisa deve estar entre 1 e 99");
        }

        if (jogadorDTO.getPosicao() == null || jogadorDTO.getPosicao().trim().isEmpty()) {
            throw new IllegalArgumentException("A posição do jogador é obrigatória");
        }

        // Verifica se o time existe
        Time time = timeRepository.findById(jogadorDTO.getTimeId())
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        // Verifica se já existe outro jogador com o mesmo número no time
        List<Jogador> jogadoresDoTime = jogadorRepository.findByTimeId(jogadorDTO.getTimeId());
        boolean numeroDuplicado = jogadoresDoTime.stream()
                .anyMatch(j -> j.getNumeroCamisa().equals(jogadorDTO.getNumeroCamisa()) &&
                        (jogadorDTO.getId() == null || !j.getId().equals(jogadorDTO.getId())));

        if (numeroDuplicado) {
            throw new IllegalArgumentException("Já existe um jogador com este número neste time");
        }
    }

    private Jogador convertToEntity(JogadorDTO dto) {
        Time time = timeRepository.findById(dto.getTimeId())
                .orElseThrow(() -> new EntityNotFoundException("Time não encontrado"));

        return Jogador.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .numeroCamisa(dto.getNumeroCamisa())
                .posicao(dto.getPosicao())
                .time(time)
                .build();
    }

    private JogadorDTO convertToDTO(Jogador jogador) {
        return JogadorDTO.builder()
                .id(jogador.getId())
                .nome(jogador.getNome())
                .numeroCamisa(jogador.getNumeroCamisa())
                .posicao(jogador.getPosicao())
                .timeId(jogador.getTime().getId())
                .build();
    }
}

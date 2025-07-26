package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.EstadioDTO;
import ifma.ppaulo.futcamp.model.Estadio;
import ifma.ppaulo.futcamp.repository.EstadioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstadioService {

    private final EstadioRepository estadioRepository;

    @Transactional
    public EstadioDTO criar(EstadioDTO estadioDTO) {
        validarEstadio(estadioDTO);

        Estadio estadio = convertToEntity(estadioDTO);
        estadio = estadioRepository.save(estadio);
        return convertToDTO(estadio);
    }

    public List<EstadioDTO> listarTodos() {
        return estadioRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EstadioDTO buscarPorId(Integer id) {
        return estadioRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado"));
    }

    @Transactional
    public EstadioDTO atualizar(Integer id, EstadioDTO estadioDTO) {
        Estadio estadioExistente = estadioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estádio não encontrado"));

        if (!estadioExistente.getNome().equals(estadioDTO.getNome()) &&
            estadioRepository.existsByNome(estadioDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um estádio com este nome");
        }

        estadioExistente.setNome(estadioDTO.getNome());
        estadioExistente.setCapacidade(estadioDTO.getCapacidade());
        estadioExistente.setCidade(estadioDTO.getCidade());
        estadioExistente.setEstado(estadioDTO.getEstado());

        estadioExistente = estadioRepository.save(estadioExistente);
        return convertToDTO(estadioExistente);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!estadioRepository.existsById(id)) {
            throw new EntityNotFoundException("Estádio não encontrado");
        }
        estadioRepository.deleteById(id);
    }

    private void validarEstadio(EstadioDTO estadioDTO) {
        if (estadioDTO.getNome() == null || estadioDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do estádio é obrigatório");
        }

        if (estadioDTO.getCapacidade() <= 0) {
            throw new IllegalArgumentException("A capacidade do estádio deve ser maior que zero");
        }

        if (estadioDTO.getCidade() == null || estadioDTO.getCidade().trim().isEmpty()) {
            throw new IllegalArgumentException("A cidade do estádio é obrigatória");
        }

        if (estadioDTO.getEstado() == null || estadioDTO.getEstado().trim().isEmpty() ||
            estadioDTO.getEstado().length() != 2) {
            throw new IllegalArgumentException("O estado deve ser informado no formato UF (2 letras)");
        }

        if (estadioDTO.getId() == null && estadioRepository.existsByNome(estadioDTO.getNome())) {
            throw new IllegalArgumentException("Já existe um estádio com este nome");
        }
    }

    private Estadio convertToEntity(EstadioDTO dto) {
        return Estadio.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .capacidade(dto.getCapacidade())
                .cidade(dto.getCidade())
                .estado(dto.getEstado().toUpperCase())
                .build();
    }

    private EstadioDTO convertToDTO(Estadio estadio) {
        return EstadioDTO.builder()
                .id(estadio.getId())
                .nome(estadio.getNome())
                .capacidade(estadio.getCapacidade())
                .cidade(estadio.getCidade())
                .estado(estadio.getEstado())
                .build();
    }
}

package ifma.ppaulo.futcamp.service;

import ifma.ppaulo.futcamp.dto.ResultadoDTO;
import ifma.ppaulo.futcamp.model.Partida;
import ifma.ppaulo.futcamp.model.Resultado;
import ifma.ppaulo.futcamp.repository.PartidaRepository;
import ifma.ppaulo.futcamp.repository.ResultadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ResultadoService {

    private final ResultadoRepository resultadoRepository;
    private final PartidaRepository partidaRepository;

    @Transactional
    public ResultadoDTO registrar(Integer partidaId, ResultadoDTO resultadoDTO) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida não encontrada"));

        if (partida.getResultado() != null) {
            throw new IllegalStateException("Esta partida já possui um resultado registrado");
        }

        validarResultado(resultadoDTO);

        Resultado resultado = convertToEntity(resultadoDTO);
        resultado = resultadoRepository.save(resultado);

        partida.setResultado(resultado);
        partidaRepository.save(partida);

        return convertToDTO(resultado);
    }

    @Transactional
    public ResultadoDTO atualizar(Integer partidaId, ResultadoDTO resultadoDTO) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida não encontrada"));

        if (partida.getResultado() == null) {
            throw new IllegalStateException("Esta partida ainda não possui um resultado registrado");
        }

        validarResultado(resultadoDTO);

        Resultado resultado = convertToEntity(resultadoDTO);
        resultado.setId(partida.getResultado().getId());
        resultado = resultadoRepository.save(resultado);

        return convertToDTO(resultado);
    }

    @Transactional
    public void deletar(Integer partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida não encontrada"));

        if (partida.getResultado() == null) {
            throw new IllegalStateException("Esta partida não possui resultado para ser deletado");
        }

        Integer resultadoId = partida.getResultado().getId();
        partida.setResultado(null);
        partidaRepository.save(partida);
        resultadoRepository.deleteById(resultadoId);
    }

    public ResultadoDTO buscarPorPartida(Integer partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new EntityNotFoundException("Partida não encontrada"));

        if (partida.getResultado() == null) {
            throw new EntityNotFoundException("Esta partida ainda não possui resultado registrado");
        }

        return convertToDTO(partida.getResultado());
    }

    private void validarResultado(ResultadoDTO resultadoDTO) {
        if (resultadoDTO.getGolsMandante() < 0) {
            throw new IllegalArgumentException("O número de gols do time mandante não pode ser negativo");
        }

        if (resultadoDTO.getGolsVisitante() < 0) {
            throw new IllegalArgumentException("O número de gols do time visitante não pode ser negativo");
        }
    }

    private Resultado convertToEntity(ResultadoDTO dto) {
        return Resultado.builder()
                .id(dto.getId())
                .golsMandante(dto.getGolsMandante())
                .golsVisitante(dto.getGolsVisitante())
                .build();
    }

    private ResultadoDTO convertToDTO(Resultado resultado) {
        return ResultadoDTO.builder()
                .id(resultado.getId())
                .golsMandante(resultado.getGolsMandante())
                .golsVisitante(resultado.getGolsVisitante())
                .build();
    }
}

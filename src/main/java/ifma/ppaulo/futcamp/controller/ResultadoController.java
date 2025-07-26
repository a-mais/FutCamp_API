package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.ResultadoDTO;
import ifma.ppaulo.futcamp.service.ResultadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resultados")
@AllArgsConstructor
public class ResultadoController {

    private final ResultadoService resultadoService;

    @PostMapping
    public ResponseEntity<ResultadoDTO> registrarResultado(@RequestBody ResultadoDTO resultadoDTO, @RequestParam Integer idPartida) {
        return ResponseEntity.ok(resultadoService.registrar(idPartida, resultadoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoDTO> buscarPorPartida(@PathVariable Integer idPartida) {
        return ResponseEntity.ok(resultadoService.buscarPorPartida(idPartida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoDTO> atualizar(@PathVariable Integer id, @RequestBody ResultadoDTO resultadoDTO) {
        return ResponseEntity.ok(resultadoService.atualizar(id, resultadoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        resultadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

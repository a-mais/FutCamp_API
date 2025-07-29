package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.ClassificacaoDTO;
import ifma.ppaulo.futcamp.dto.ResultadoDTO;
import ifma.ppaulo.futcamp.service.CampeonatoService;
import ifma.ppaulo.futcamp.service.ResultadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados")
@AllArgsConstructor
public class ResultadoController {

    private final ResultadoService resultadoService;
    private final CampeonatoService campeonatoService;

    @PostMapping
    public ResponseEntity<ResultadoDTO> registrarResultado(@RequestBody ResultadoDTO resultadoDTO, @RequestParam Integer idPartida) {
        return ResponseEntity.ok(resultadoService.registrar(idPartida, resultadoDTO));
    }

    @GetMapping("/{idPartida}")
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

    @GetMapping("/tabela/{campeonatoId}")
    public ResponseEntity<List<ClassificacaoDTO>> listarTabelaClassificacao(@PathVariable Integer campeonatoId) {
        return ResponseEntity.ok(campeonatoService.getTabela(campeonatoId));
    }
}

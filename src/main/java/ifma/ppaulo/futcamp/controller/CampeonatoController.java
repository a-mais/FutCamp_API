package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.CampeonatoDTO;
import ifma.ppaulo.futcamp.dto.ClassificacaoDTO;
import ifma.ppaulo.futcamp.service.CampeonatoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
@AllArgsConstructor
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    @PostMapping
    public ResponseEntity<CampeonatoDTO> criar(@RequestBody CampeonatoDTO campeonatoDTO) {
        return ResponseEntity.ok(campeonatoService.criar(campeonatoDTO));
    }

    @GetMapping
    public ResponseEntity<List<CampeonatoDTO>> listarTodos() {
        return ResponseEntity.ok(campeonatoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(campeonatoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampeonatoDTO> atualizar(@PathVariable Integer id, @RequestBody CampeonatoDTO campeonatoDTO) {
        return ResponseEntity.ok(campeonatoService.atualizar(id, campeonatoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        campeonatoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tabela")
    public ResponseEntity<List<ClassificacaoDTO>> getTabela(@PathVariable Integer id) {
        return ResponseEntity.ok(campeonatoService.getTabela(id));
    }
}

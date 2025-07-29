package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.PartidaDTO;
import ifma.ppaulo.futcamp.service.PartidaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partidas")
@AllArgsConstructor
public class PartidaController {

    private final PartidaService partidaService;

    @PostMapping
    public ResponseEntity<PartidaDTO> criar(@RequestBody PartidaDTO partidaDTO) {
        return ResponseEntity.ok(partidaService.criar(partidaDTO));
    }

    @GetMapping
    public ResponseEntity<List<PartidaDTO>> listarTodos() {
        return ResponseEntity.ok(partidaService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(partidaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidaDTO> atualizar(@PathVariable Integer id, @RequestBody PartidaDTO partidaDTO) {
        return ResponseEntity.ok(partidaService.atualizar(id, partidaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        partidaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/campeonato/{id}")
    public ResponseEntity<List<PartidaDTO>> listarPartidasPorCampeonato(@PathVariable Integer id) {
        return ResponseEntity.ok(partidaService.buscarPorCampeonato(id));
    }
}

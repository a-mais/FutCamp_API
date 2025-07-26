package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.JogadorDTO;
import ifma.ppaulo.futcamp.service.JogadorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogadores")
@AllArgsConstructor
public class JogadorController {

    private final JogadorService jogadorService;

    @PostMapping
    public ResponseEntity<JogadorDTO> criar(@RequestBody JogadorDTO jogadorDTO) {
        return ResponseEntity.ok(jogadorService.criar(jogadorDTO));
    }

    @GetMapping
    public ResponseEntity<List<JogadorDTO>> listarTodos() {
        return ResponseEntity.ok(jogadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogadorDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(jogadorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogadorDTO> atualizar(@PathVariable Integer id, @RequestBody JogadorDTO jogadorDTO) {
        return ResponseEntity.ok(jogadorService.atualizar(id, jogadorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        jogadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/time/{timeId}")
    public ResponseEntity<List<JogadorDTO>> buscarPorTime(@PathVariable Integer timeId) {
        return ResponseEntity.ok(jogadorService.buscarPorTime(timeId));
    }
}

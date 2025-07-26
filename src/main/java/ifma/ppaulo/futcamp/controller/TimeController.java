package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.TimeDTO;
import ifma.ppaulo.futcamp.service.TimeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/times")
@AllArgsConstructor
public class TimeController {

    private final TimeService timeService;

    @PostMapping
    public ResponseEntity<TimeDTO> criar(@RequestBody TimeDTO timeDTO) {
        return ResponseEntity.ok(timeService.criar(timeDTO));
    }

    @GetMapping
    public ResponseEntity<List<TimeDTO>> listarTodos() {
        return ResponseEntity.ok(timeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(timeService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeDTO> atualizar(@PathVariable Integer id, @RequestBody TimeDTO timeDTO) {
        return ResponseEntity.ok(timeService.atualizar(id, timeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        timeService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/jogadores")
    public ResponseEntity<List<TimeDTO>> buscarJogadoresDoTime(@PathVariable Integer id) {
        return ResponseEntity.ok(timeService.buscarJogadoresDoTime(id));
    }
}

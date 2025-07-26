package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.EstadioDTO;
import ifma.ppaulo.futcamp.service.EstadioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estadios")
@AllArgsConstructor
public class EstadioController {

    private final EstadioService estadioService;

    @PostMapping
    public ResponseEntity<EstadioDTO> criar(@RequestBody EstadioDTO estadioDTO) {
        return ResponseEntity.ok(estadioService.criar(estadioDTO));
    }

    @GetMapping
    public ResponseEntity<List<EstadioDTO>> listarTodos() {
        return ResponseEntity.ok(estadioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadioDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(estadioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadioDTO> atualizar(@PathVariable Integer id, @RequestBody EstadioDTO estadioDTO) {
        return ResponseEntity.ok(estadioService.atualizar(id, estadioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        estadioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

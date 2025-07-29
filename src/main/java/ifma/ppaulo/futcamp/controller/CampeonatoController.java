package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.CampeonatoDTO;
import ifma.ppaulo.futcamp.dto.ClassificacaoDTO;
import ifma.ppaulo.futcamp.dto.PartidaDTO;
import ifma.ppaulo.futcamp.dto.TimeDTO;
import ifma.ppaulo.futcamp.service.CampeonatoService;
import ifma.ppaulo.futcamp.service.PartidaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campeonatos")
@AllArgsConstructor
public class CampeonatoController {

    private final CampeonatoService campeonatoService;
    private final PartidaService partidaService;
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

    @GetMapping("/{id}/times")
    public ResponseEntity<List<TimeDTO>> listarTimesDoCampeonato(@PathVariable Integer id) {
        return ResponseEntity.ok(campeonatoService.listarTimesDoCampeonato(id));
    }

    @GetMapping("/{id}/partidas/ocorridas")
    public ResponseEntity<List<PartidaDTO>> listarPartidasOcorridas(@PathVariable Integer id) {
        return ResponseEntity.ok(partidaService.listarPartidasOcorridas(id));
    }

    @GetMapping("/{id}/partidas/nao-ocorridas")
    public ResponseEntity<List<PartidaDTO>> listarPartidasNaoOcorridas(@PathVariable Integer id) {
        return ResponseEntity.ok(partidaService.listarPartidasNaoOcorridas(id));
    }

    @GetMapping("/{id}/tabela")
    public ResponseEntity<List<ClassificacaoDTO>> getTabela(@PathVariable Integer id) {
        return ResponseEntity.ok(campeonatoService.getTabela(id));
    }

    @PostMapping("/{id}/times/{timeId}")
    public ResponseEntity<TimeDTO> adicionarTimeCampeonato(
            @PathVariable Integer id,
            @PathVariable Integer timeId) {
        return ResponseEntity.ok(campeonatoService.adicionarTimeCampeonato(id, timeId));
    }
}

package ifma.ppaulo.futcamp.controller;

import ifma.ppaulo.futcamp.dto.TabelaDTO;
import ifma.ppaulo.futcamp.service.TabelaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tabela")
@AllArgsConstructor
public class TabelaController {

    private final TabelaService tabelaService;

    @GetMapping("/campeonato/{campeonatoId}")
    public ResponseEntity<List<TabelaDTO>> getClassificacao(@PathVariable Integer campeonatoId) {
        return ResponseEntity.ok(tabelaService.getClassificacao(campeonatoId));
    }

    @GetMapping("/time/{timeId}/campeonato/{campeonatoId}")
    public ResponseEntity<TabelaDTO> getClassificacaoTime(
            @PathVariable Integer timeId,
            @PathVariable Integer campeonatoId) {
        return ResponseEntity.ok(tabelaService.getClassificacaoTime(timeId, campeonatoId));
    }

    @GetMapping("/time/{timeId}")
    public ResponseEntity<List<TabelaDTO>> getClassificacaoTimeEmTodosCampeonatos(
            @PathVariable Integer timeId) {
        return ResponseEntity.ok(tabelaService.getClassificacaoTimeEmTodosCampeonatos(timeId));
    }

    @PostMapping("/time/{timeId}/campeonato/{campeonatoId}")
    public ResponseEntity<TabelaDTO> inicializarTimeNoCampeonato(
            @PathVariable Integer timeId,
            @PathVariable Integer campeonatoId) {
        return ResponseEntity.ok(tabelaService.inicializarTimeNoCampeonato(timeId, campeonatoId));
    }
}

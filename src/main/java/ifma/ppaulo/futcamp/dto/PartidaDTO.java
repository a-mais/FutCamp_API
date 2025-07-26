package ifma.ppaulo.futcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO {
    private Integer id;
    private LocalDate data;
    private Integer timeMandanteId;
    private Integer timeVisitanteId;
    private Integer campeonatoId;
    private ResultadoDTO resultado;
}

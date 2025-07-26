package ifma.ppaulo.futcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadioDTO {
    private Integer id;
    private String nome;
    private Integer capacidade;
    private String cidade;
    private String estado;
}

package ifma.ppaulo.futcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeDTO {
    private Integer id;
    private String nome;
    private String classificacao;
    private String cidade;
    private String estado;
}

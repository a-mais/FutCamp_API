package ifma.ppaulo.futcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JogadorDTO {
    private Integer id;
    private String nome;
    private LocalDate dataNascimento;
    private int numeroCamisa;
    private String posicao;
    private int timeId;
}

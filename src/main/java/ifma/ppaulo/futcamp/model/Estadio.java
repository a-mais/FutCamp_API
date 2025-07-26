package ifma.ppaulo.futcamp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estadio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String endereco;

    private int capacidade;

    private String cidade;

    private String estado;
}

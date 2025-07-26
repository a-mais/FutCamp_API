package ifma.ppaulo.futcamp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "resultado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "gols_mandante", nullable = false)
    private Integer golsMandante;

    @Column(name = "gols_visitante", nullable = false)
    private Integer golsVisitante;

    @OneToOne(mappedBy = "resultado")
    private Partida partida;

    @PrePersist
    @PreUpdate
    private void validarGols() {
        if (golsMandante < 0 || golsVisitante < 0) {
            throw new IllegalStateException("O número de gols não pode ser negativo");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resultado)) return false;
        Resultado resultado = (Resultado) o;
        return getId() != null && getId().equals(resultado.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

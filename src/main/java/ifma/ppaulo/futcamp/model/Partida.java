package ifma.ppaulo.futcamp.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "partida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_mandante_id", nullable = false)
    private Time timeMandante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_visitante_id", nullable = false)
    private Time timeVisitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resultado_id")
    private Resultado resultado;

    @PrePersist
    @PreUpdate
    private void validarTimes() {
        if (timeMandante != null && timeVisitante != null &&
            timeMandante.getId().equals(timeVisitante.getId())) {
            throw new IllegalStateException("Time mandante e visitante não podem ser o mesmo");
        }
    }
}

package ifma.ppaulo.futcamp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jogador",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"time_id", "numero_camisa"})
    })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "numero_camisa", nullable = false)
    private Integer numeroCamisa;

    @Column(nullable = false)
    private String posicao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id", nullable = false)
    private Time time;

    @PrePersist
    @PreUpdate
    private void validarNumeroCamisa() {
        if (numeroCamisa <= 0 || numeroCamisa > 99) {
            throw new IllegalStateException("O número da camisa deve estar entre 1 e 99");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jogador)) return false;
        Jogador jogador = (Jogador) o;
        return getId() != null && getId().equals(jogador.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

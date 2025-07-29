package ifma.ppaulo.futcamp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tabela")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tabela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id", nullable = false)
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campeonato_id", nullable = false)
    private Campeonato campeonato;

    private Integer pontos;
    private Integer jogos;
    private Integer vitorias;
    private Integer empates;
    private Integer derrotas;
    private Integer golsPro;
    private Integer golsContra;
    private Integer saldoGols;

    @PrePersist
    @PreUpdate
    private void calcularSaldoGols() {
        this.saldoGols = this.golsPro - this.golsContra;
    }

    public void registrarVitoria(int golsPro, int golsContra) {
        this.vitorias++;
        this.jogos++;
        this.pontos += 3;
        this.golsPro += golsPro;
        this.golsContra += golsContra;
        calcularSaldoGols();
    }

    public void registrarEmpate(int golsPro, int golsContra) {
        this.empates++;
        this.jogos++;
        this.pontos += 1;
        this.golsPro += golsPro;
        this.golsContra += golsContra;
        calcularSaldoGols();
    }

    public void registrarDerrota(int golsPro, int golsContra) {
        this.derrotas++;
        this.jogos++;
        this.golsPro += golsPro;
        this.golsContra += golsContra;
        calcularSaldoGols();
    }
}

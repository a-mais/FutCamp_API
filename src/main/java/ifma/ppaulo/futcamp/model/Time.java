package ifma.ppaulo.futcamp.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "time")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nome;

    private String cidade;

    private String estado;

    @OneToMany(mappedBy = "time", fetch = FetchType.LAZY)
    private Set<Jogador> jogadores = new HashSet<>();

    @ManyToMany(mappedBy = "times", fetch = FetchType.LAZY)
    private Set<Campeonato> campeonatos = new HashSet<>();

    @OneToMany(mappedBy = "timeMandante", fetch = FetchType.LAZY)
    private Set<Partida> partidasMandante = new HashSet<>();

    @OneToMany(mappedBy = "timeVisitante", fetch = FetchType.LAZY)
    private Set<Partida> partidasVisitante = new HashSet<>();

    public void addJogador(Jogador jogador) {
        jogadores.add(jogador);
        jogador.setTime(this);
    }

    public void removeJogador(Jogador jogador) {
        jogadores.remove(jogador);
        jogador.setTime(null);
    }

    public void addCampeonato(Campeonato campeonato) {
        campeonatos.add(campeonato);
        campeonato.getTimes().add(this);
    }

    public void removeCampeonato(Campeonato campeonato) {
        campeonatos.remove(campeonato);
        campeonato.getTimes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;
        Time time = (Time) o;
        return getId() != null && getId().equals(time.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

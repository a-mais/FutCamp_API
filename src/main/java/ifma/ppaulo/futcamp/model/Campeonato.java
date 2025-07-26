package ifma.ppaulo.futcamp.model;


import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campeonato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campeonato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer ano;

    private String nome;

    @ManyToMany
    @JoinTable(
        name = "time_campeonato",
        joinColumns = @JoinColumn(name = "campeonato_id"),
        inverseJoinColumns = @JoinColumn(name = "time_id")
    )
    private Set<Time> times = new HashSet<>();

    @OneToMany(mappedBy = "campeonato", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Partida> partidas = new HashSet<>();

    public void addTime(Time time) {
        times.add(time);
    }

    public void removeTime(Time time) {
        times.remove(time);
    }
}

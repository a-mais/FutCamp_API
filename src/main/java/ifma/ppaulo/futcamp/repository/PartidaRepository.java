package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Campeonato;
import ifma.ppaulo.futcamp.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {

    List<Partida> findByCampeonatoId(Integer campeonatoId);

    @Query("SELECT p FROM Partida p LEFT JOIN FETCH p.timeMandante LEFT JOIN FETCH p.timeVisitante WHERE p.id = :id")
    Optional<Partida> findByIdWithRelationships(Integer id);

    @Query("SELECT p FROM Partida p LEFT JOIN FETCH p.timeMandante LEFT JOIN FETCH p.timeVisitante WHERE p.campeonato.id = :campeonatoId")
    List<Partida> findByCampeonatoIdWithTimes(Integer campeonatoId);

    List<Partida> findByCampeonatoAndResultadoIsNotNull(Campeonato campeonato);

    List<Partida> findByCampeonatoAndResultadoIsNull(Campeonato campeonato);
}


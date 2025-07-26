package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {
    List<Partida> findByCampeonatoId(Integer campeonatoId);
    List<Partida> findByTimeMandanteIdOrTimeVisitanteId(Integer timeMandanteId, Integer timeVisitanteId);

    @Query("SELECT p FROM Partida p " +
           "LEFT JOIN FETCH p.timeMandante " +
           "LEFT JOIN FETCH p.timeVisitante " +
           "LEFT JOIN FETCH p.resultado " +
           "WHERE p.campeonato.id = :campeonatoId")
    List<Partida> findByCampeonatoIdWithTimes(@Param("campeonatoId") Integer campeonatoId);

    @Query("SELECT p FROM Partida p " +
           "LEFT JOIN FETCH p.timeMandante " +
           "LEFT JOIN FETCH p.timeVisitante " +
           "LEFT JOIN FETCH p.resultado " +
           "WHERE p.id = :id")
    Optional<Partida> findByIdWithRelationships(@Param("id") Integer id);
}

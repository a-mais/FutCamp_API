package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<Time, Integer> {

    @Query("SELECT t FROM Time t JOIN t.campeonatos c WHERE c.id = :campeonatoId")
    List<Time> findByCampeonatoId(@Param("campeonatoId") Integer campeonatoId);

    boolean existsByNome(String nome);

    @Query("SELECT t FROM Time t LEFT JOIN FETCH t.jogadores WHERE t.id = :id")
    Optional<Time> findByIdWithJogadores(@Param("id") Integer id);
}

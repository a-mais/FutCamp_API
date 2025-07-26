package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Campeonato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer> {
    List<Campeonato> findByAno(Integer ano);
    Optional<Campeonato> findByNomeAndAno(String nome, Integer ano);
    boolean existsByNomeAndAno(String nome, Integer ano);

    @Query("SELECT c FROM Campeonato c LEFT JOIN FETCH c.times WHERE c.id = :id")
    Optional<Campeonato> findByIdWithTimes(@Param("id") Integer id);
}

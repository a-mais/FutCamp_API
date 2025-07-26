package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<Estadio, Integer> {
    boolean existsByNome(String nome);
}

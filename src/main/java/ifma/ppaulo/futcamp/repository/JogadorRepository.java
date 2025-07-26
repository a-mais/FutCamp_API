package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JogadorRepository extends JpaRepository<Jogador, Integer> {
    List<Jogador> findByTimeId(Integer timeId);
    boolean existsByNome(String nome);
}

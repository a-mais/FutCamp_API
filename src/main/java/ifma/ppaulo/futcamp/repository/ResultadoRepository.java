package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Integer> {
    // Métodos personalizados podem ser adicionados conforme necessário
}

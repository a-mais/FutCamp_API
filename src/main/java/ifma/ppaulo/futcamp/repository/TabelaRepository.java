package ifma.ppaulo.futcamp.repository;

import ifma.ppaulo.futcamp.model.Tabela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TabelaRepository extends JpaRepository<Tabela, Integer> {
    @Query("SELECT t FROM Tabela t WHERE t.campeonato.id = :campeonatoId ORDER BY t.pontos DESC, t.vitorias DESC, t.saldoGols DESC")
    List<Tabela> findByCampeonatoIdOrderByPontosAndVitoriasAndSaldoGols(Integer campeonatoId);

    Optional<Tabela> findByTimeIdAndCampeonatoId(Integer timeId, Integer campeonatoId);

    List<Tabela> findByTimeId(Integer timeId);
}

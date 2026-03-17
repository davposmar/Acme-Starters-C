
package acme.entities.fundraising;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface StrategyRepository extends AbstractRepository {

	@Query("SELECT SUM(t.expectedPercentage) FROM Tactic t WHERE t.strategy.id = :strategyId")
	Double expectedPercentage(@Param("strategyId") Integer strategyId);

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findStrategyByTicker(String ticker);

	@Query("select s from Strategy s where s.name = :name")
	Strategy findStrategyByName(String name);

	@Query("Select COUNT(t) from Tactic t where t.strategy.id = :strategyId")
	Long countTacticsByStrategyId(int strategyId);
}

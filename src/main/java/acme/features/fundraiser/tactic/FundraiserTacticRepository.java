
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.fundraising.Strategy;
import acme.entities.fundraising.Tactic;

@Repository
public interface FundraiserTacticRepository extends AbstractRepository {

	@Query("Select s from Strategy s where s.id = :strategyId")
	Strategy findStrategyById(int strategyId);

	@Query("Select t from Tactic t where t.id = :id")
	Tactic findTacticById(int id);

	@Query("Select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticsByStrategyId(int strategyId);
}

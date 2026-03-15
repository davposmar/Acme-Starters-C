
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.fundraising.Strategy;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("Select s from Strategy s where s.fundraiser.id = :fundraiserId")
	Collection<Strategy> findStrategiesByFundraiserId(int fundraiserId);

	@Query("Select s from Strategy s where s.id = :strategyId")
	Strategy findStrategyById(int strategyId);
}

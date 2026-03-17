
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.fundraising.Strategy;
import acme.realms.Fundraiser;

@Repository
public interface AnyStrategyRepository extends AbstractRepository {

	@Query("Select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("Select s from Strategy s where s.draftMode = false")
	Collection<Strategy> findPublishedStrategies();

	@Query("Select f from Fundraiser f")
	Collection<Fundraiser> findAllFundraisers();
}

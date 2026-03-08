
package acme.features.inventor.invention;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :inventionId ")
	Double computeCostOfInvention(int inventionId);

	@Query("select i from Invention i where i.ticker = :ticker")
	Invention findInventionByTicker(String ticker);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	Long countPartsOfInvention(int inventionId);
}

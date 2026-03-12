
package acme.features.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	@Query("Select sum(d.money.amount) from Donation d where d.sponsorship.id = :sponsorshipId")
	Double calcTotalMoneySponsorship(int sponsorshipId);

	@Query("select ss from Sponsorship ss where ss.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);
}

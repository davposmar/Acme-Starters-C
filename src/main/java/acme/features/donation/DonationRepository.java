
package acme.features.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query("Select sum(d.money.amount) from Donation d where d.sponsorship.id = :sponsorshipId")
	Double calcTotalMoneySponsorship(int sponsorshipId);

}


package acme.features.donation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query("Select COUNT(d) from Donation d where d.sponsorship.id = :sponsorshipId")
	Integer findCountDonationsBySponsorshipId(int sponsorshipId);
}

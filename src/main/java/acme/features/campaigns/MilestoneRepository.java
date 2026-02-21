
package acme.features.campaigns;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface MilestoneRepository extends AbstractRepository {

	@Query("Select SUM(ms.effort) from Milestone ms where ms.campaign.id = :campaignId")
	Double sumEffortsByCampaignId(int campaignId);

}


package acme.entities.campaigns;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("Select SUM(ms.effort) from Milestone ms where ms.campaign.id = :campaignId")
	Double sumEffortsByCampaignId(int campaignId);

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

	@Query("Select COUNT(ms) from Milestone ms where ms.campaign.id = :campaignId")
	Long countMilestoneByCampaignId(int campaignId);
}

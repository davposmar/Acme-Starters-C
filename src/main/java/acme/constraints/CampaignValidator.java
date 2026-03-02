
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaigns.Campaign;
import acme.features.campaigns.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CampaignRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		// HINT: campaign can be null
		assert context != null;

		boolean result;

		if (campaign == null)
			result = true;
		else {
			{
				boolean uniqueCampaign;
				Campaign existingCampaign;

				existingCampaign = this.repository.findCampaignByTicker(campaign.getTicker());
				uniqueCampaign = existingCampaign == null || existingCampaign.equals(campaign);

				super.state(context, uniqueCampaign, "ticker", "acme.validation.campaign.duplicated-ticker.message");
			}
			{
				boolean correctMilestoneNumber;

				correctMilestoneNumber = campaign.getDraftMode() || this.repository.countMilestoneByCampaignId(campaign.getId()) > 0;

				super.state(context, correctMilestoneNumber, "draftMode", "acme.validation.campaign.milestone.message");
			}
			{
				Date startMoment = campaign.getStartMoment();
				Date endMoment = campaign.getEndMoment();
				boolean isValidInterval;

				if (startMoment != null && endMoment != null) {
					isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
					super.state(context, isValidInterval, "endMoment", "acme.validation.campaign.moments.message");
				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}

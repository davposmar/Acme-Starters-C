
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorships.Sponsorship;
import acme.features.donation.DonationRepository;
import acme.features.donation.SponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorshipRepository	repository;

	@Autowired
	private DonationRepository		donationRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		if (sponsorship == null)
			result = true;
		else {
			{
				boolean uniqueSponsorship;
				Sponsorship existingSponsorship;

				existingSponsorship = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
				uniqueSponsorship = existingSponsorship == null || existingSponsorship.equals(sponsorship);

				super.state(context, uniqueSponsorship, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
			}
			{
				boolean correctMinimunDonations;

				correctMinimunDonations = sponsorship.getDraftMode() || this.donationRepository.findCountDonationsBySponsorshipId(sponsorship.getId()) >= 1;

				super.state(context, correctMinimunDonations, "draftMode", "acme.validation.audit-report.minimun-donations.message");
			}
			{
				Date startMoment = sponsorship.getStartMoment();
				Date endMoment = sponsorship.getEndMoment();
				boolean isValidInterval;

				if (!sponsorship.getDraftMode() && startMoment != null && endMoment != null) {
					isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
					super.state(context, isValidInterval, "endMoment", "acme.validation.sponsorship.moments.message");
				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}

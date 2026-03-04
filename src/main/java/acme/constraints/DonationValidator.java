
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorships.Donation;

@Validator
public class DonationValidator extends AbstractValidator<ValidDonation, Donation> {

	// Internal state ---------------------------------------------------------

	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidDonation annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Donation donation, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		if (donation == null)
			result = true;
		else {
			{
				String validCurrency = "EUR";
				boolean validMoney;
				validMoney = donation.getMoney().getAmount() != null && donation.getMoney().getCurrency() != null && donation.getMoney().getCurrency().equals(validCurrency);

				super.state(context, validMoney, "money", "acme.validation.donation.money-currency.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}

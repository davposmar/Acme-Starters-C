
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;
import acme.entities.inventions.Part;

public class PartValidator extends AbstractValidator<ValidPart, Part> {
	// Internal state ---------------------------------------------------------
	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidPart annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Part part, final ConstraintValidatorContext context) {
		// HINT: part can be null
		assert context != null;

		boolean result;

		if (part == null)
			result = true;
		else {
			{
				boolean correctCurrency;
				Money cost = part.getCost();
				boolean itHasAmountAndCurrency = cost != null && cost.getAmount() != null && cost.getCurrency() != null;

				correctCurrency = itHasAmountAndCurrency && cost.getCurrency().equals("EUR");

				super.state(context, correctCurrency, "*", "acme.validation.job.workload.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}

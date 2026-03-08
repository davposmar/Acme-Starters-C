
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.features.inventor.invention.InventionRepository;

public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		final long MIN_PARTS_OF_INVENTION = 1L;

		if (invention == null)
			result = true;
		else {
			{
				boolean uniqueInvention;
				Invention existingInvention;
				existingInvention = this.repository.findInventionByTicker(invention.getTicker());
				uniqueInvention = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
			}
			{
				boolean correctParts;
				boolean publicMode = !invention.getDraftMode() && this.repository.countPartsOfInvention(invention.getId()) >= MIN_PARTS_OF_INVENTION;
				correctParts = invention.getDraftMode() || publicMode;

				super.state(context, correctParts, "*", "acme.validation.job.workload.message");
			}
			{
				boolean correctDates;
				if (invention.getEndMoment() != null && invention.getStartMoment() != null) {
					correctDates = MomentHelper.isAfter(invention.getEndMoment(), invention.getStartMoment());
					super.state(context, correctDates, "deadline", "acme.validation.job.deadline.message");
				}

			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}

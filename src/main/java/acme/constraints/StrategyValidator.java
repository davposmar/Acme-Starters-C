
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.fundraising.Strategy;
import acme.entities.fundraising.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		// HINT: strategy can be null
		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				boolean correctTacticNumber;
				Integer id = strategy.getId();
				if (id != null) {
					correctTacticNumber = strategy.getDraftMode() || this.repository.countTacticsByStrategyId(id) > 0;

					super.state(context, correctTacticNumber, "*", "acme.validation.strategy.tactic.message");
				}

			}
			{
				Date startMoment = strategy.getStartMoment();
				Date endMoment = strategy.getEndMoment();
				boolean isValidInterval;

				if (startMoment != null && endMoment != null) {
					isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
					super.state(context, isValidInterval, "endMoment", "acme.validation.strategy.moments.message");
				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}


package acme.features.inventor.invention;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findinventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getDraftMode() && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		final long MIN_PARTS_OF_INVENTION = 1L;
		{
			Date startMoment, endMoment;
			boolean isValidInterval;
			boolean isStartFuture;
			boolean isEndFuture;

			startMoment = this.invention.getStartMoment();
			endMoment = this.invention.getEndMoment();

			if (startMoment != null && endMoment != null) {
				isStartFuture = MomentHelper.isFuture(startMoment);
				isEndFuture = MomentHelper.isFuture(endMoment);
				isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
				super.state(isValidInterval, "*", "acme.validation.invention.moments.message");
				super.state(isStartFuture, "startMoment", "acme.validation.invention.startMoment.message");
				super.state(isEndFuture, "endMoment", "acme.validation.invention.endMoment.message");
			}
		}
		{
			Integer id;
			boolean hasMinParts;

			id = this.invention.getId();

			if (id != null) {
				hasMinParts = this.repository.countPartsOfInvention(id) >= MIN_PARTS_OF_INVENTION;
				super.state(hasMinParts, "*", "acme.validation.invention.part.message");
			}
		}
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "inventor.identity.fullName");
		tuple.put("monthsActive", this.invention.getMonthsActive());
		tuple.put("cost", this.invention.getCost());
	}
}

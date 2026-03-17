
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.entities.inventions.PartKind;

public class AnyPartShowService extends AbstractService<Any, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPartRepository	repository;

	private Part				part;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices kinds;
		Tuple tuple;

		kinds = SelectChoices.from(PartKind.class, this.part.getKind());

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind", "invention.name");
		tuple.put("kinds", kinds);
	}
}

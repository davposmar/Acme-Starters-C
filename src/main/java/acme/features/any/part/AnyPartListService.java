
package acme.features.any.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

public class AnyPartListService extends AbstractService<Any, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyPartRepository	repository;

	private Invention			invention;
	private Collection<Part>	parts;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInventionId(inventionId);
		this.parts = this.repository.findPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && !this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, //
			"name", "description", "cost", "kind");
	}

}


package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Tactic;
import acme.entities.fundraising.TacticKind;

@Service
public class AnyTacticShowService extends AbstractService<Any, Tactic> {

	@Autowired
	private AnyTacticRepository	repository;

	private Tactic				tactic;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.tactic != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices kinds;
		Tuple tuple;

		kinds = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("kinds", kinds);
		tuple.put("strategy", this.tactic.getStrategy().getName());
	}
}

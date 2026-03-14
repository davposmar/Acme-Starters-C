
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.realms.Fundraiser;

@Service
public class AnyStrategyShowService extends AbstractService<Any, Strategy> {

	@Autowired
	private AnyStrategyRepository	repository;

	private Strategy				strategy;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && !this.strategy.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Collection<Fundraiser> fundraisers;
		SelectChoices choices;
		Tuple tuple;

		fundraisers = this.repository.findAllFundraisers();
		choices = SelectChoices.from(fundraisers, "identity.fullName", this.strategy.getFundraiser());
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "expectedPercentage");
		tuple.put("fundraiser", choices.getSelected().getKey());
		tuple.put("fundraisers", choices);
	}
}


package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.realms.Fundraiser;

public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && (this.strategy.getFundraiser().isPrincipal() || !this.strategy.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "fundraiser.identity.fullName");
		tuple.put("monthsActive", this.strategy.getMonthsActive());
		tuple.put("expectedPercentage", this.strategy.getExpectedPercentage());
	}
}

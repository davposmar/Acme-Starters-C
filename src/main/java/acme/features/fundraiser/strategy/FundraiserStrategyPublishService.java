/*
 * FundraiserStrategyPublishService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.strategy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.fundraising.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);

		{
			Date startMoment, endMoment;
			boolean isValidInterval;
			boolean isStartFuture;
			boolean isEndFuture;

			startMoment = this.strategy.getStartMoment();
			endMoment = this.strategy.getEndMoment();

			if (startMoment != null && endMoment != null) {
				isStartFuture = MomentHelper.isFuture(startMoment);
				isEndFuture = MomentHelper.isFuture(endMoment);
				isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
				super.state(isValidInterval, "*", "acme.validation.strategy.moments.message");
				super.state(isStartFuture, "startMoment", "acme.validation.strategy.startMoment.message");
				super.state(isEndFuture, "endMoment", "acme.validation.strategy.endMoment.message");
			}
		}
		{
			Integer id;
			boolean hasTactics;

			id = this.strategy.getId();

			if (id != null) {
				hasTactics = this.repository.countTacticsByStrategyId(id) > 0;
				super.state(hasTactics, "*", "acme.validation.strategy.tactic.message");
			}
		}
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "fundraiser.identity.fullName");
		tuple.put("monthsActive", this.strategy.getMonthsActive());
		tuple.put("expectedPercentage", this.strategy.getExpectedPercentage());
	}

}

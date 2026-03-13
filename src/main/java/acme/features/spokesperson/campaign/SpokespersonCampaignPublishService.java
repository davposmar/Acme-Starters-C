/*
 * SpokespersonCampaignPublishService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.spokesperson.campaign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getDraftMode() && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

		{
			Date startMoment, endMoment;
			boolean isValidInterval;
			boolean isStartFuture;
			boolean isEndFuture;

			startMoment = this.campaign.getStartMoment();
			endMoment = this.campaign.getEndMoment();

			if (startMoment != null && endMoment != null) {
				isStartFuture = MomentHelper.isFuture(startMoment);
				isEndFuture = MomentHelper.isFuture(endMoment);
				isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
				super.state(isValidInterval, "*", "acme.validation.campaign.moments.message");
				super.state(isStartFuture, "startMoment", "acme.validation.campaign.startMoment.message");
				super.state(isEndFuture, "endMoment", "acme.validation.campaign.endMoment.message");
			}
		}
		{
			Integer id;
			boolean hasMilestones;

			id = this.campaign.getId();

			if (id != null) {
				hasMilestones = this.repository.countMilestonesByCampaignId(id) > 0;
				super.state(hasMilestones, "*", "acme.validation.campaign.milestone.message");
			}
		}
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "spokesperson.identity.fullName");
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		tuple.put("effort", this.campaign.getEffort());
	}

}

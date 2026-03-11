/*
 * SponsorSponsorshipShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository	repository;

	private Sponsorship					sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && !this.sponsorship.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "sponsor.identity.fullName");
		tuple.put("monthsActive", this.sponsorship.getMonthsActive());
		tuple.put("totalMoney", this.sponsorship.getTotalMoney());
		tuple.put("sponsorId", this.sponsorship.getSponsor().getId());
	}

}

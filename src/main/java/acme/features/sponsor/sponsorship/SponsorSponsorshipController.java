/*
 * SponsorSponsorshipController.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", SponsorSponsorshipListService.class);
		super.addBasicCommand("show", SponsorSponsorshipShowService.class);
		super.addBasicCommand("create", SponsorSponsorshipCreateService.class);
		super.addBasicCommand("update", SponsorSponsorshipUpdateService.class);
		super.addBasicCommand("delete", SponsorSponsorshipDeleteService.class);

		super.addCustomCommand("publish", "update", SponsorSponsorshipPublishService.class);
	}

}

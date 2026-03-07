/*
 * AnyAuditSectionShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditSection;

@Service
public class AnyAuditSectionShowService extends AbstractService<Any, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditSectionRepository	repository;

	private AuditSection				auditSection;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditSection = this.repository.findAuditSectionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditSection != null && !this.auditSection.getAuditReport().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

}

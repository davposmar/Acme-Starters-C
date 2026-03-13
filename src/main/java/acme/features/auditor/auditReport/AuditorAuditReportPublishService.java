/*
 * AnyAuditorShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.auditReport;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audits.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorAuditReportPublishService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditReportRepository	repository;

	private AuditReport						auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);

		{
			Date startMoment = this.auditReport.getStartMoment();
			Date endMoment = this.auditReport.getEndMoment();
			boolean isValidInterval;
			boolean isStartFuture;
			boolean isEndFuture;

			if (startMoment != null && endMoment != null) {
				isStartFuture = MomentHelper.isFuture(startMoment);
				isEndFuture = MomentHelper.isFuture(endMoment);
				isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
				super.state(isValidInterval, "*", "acme.validation.audit-report.interval.message");
				super.state(isStartFuture, "startMoment", "acme.validation.audit-report.startMoment.message");
				super.state(isEndFuture, "endMoment", "acme.validation.audit-report.endMoment.message");
			}
		}
		{
			boolean correctMinimunSections;
			Integer id = this.auditReport.getId();
			if (id != null) {
				correctMinimunSections = this.repository.findCountAuditReportsByAuditReportId(id) >= 1;

				super.state(correctMinimunSections, "*", "acme.validation.audit-report.minimun-sections.message");
			}
		}
	}

	@Override
	public void execute() {
		this.auditReport.setDraftMode(false);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "auditor.identity.fullName");
		tuple.put("monthsActive", this.auditReport.getMonthsActive());
		tuple.put("hours", this.auditReport.getHours());
	}

}

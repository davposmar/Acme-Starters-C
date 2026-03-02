
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.audits.AuditReport;
import acme.features.audits.AuditReportRepository;
import acme.features.audits.AuditSectionRepository;

@Validator
public class AuditReportValidator extends AbstractValidator<ValidAuditReport, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditReportRepository	repository;

	@Autowired
	private AuditSectionRepository	sectionRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidAuditReport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {
		// HINT: job can be null
		assert context != null;

		boolean result;

		if (auditReport == null)
			result = true;
		else {
			{
				boolean uniqueReport;
				AuditReport existingReport;

				existingReport = this.repository.findAuditReportByTicker(auditReport.getTicker());
				uniqueReport = existingReport == null || existingReport.equals(auditReport);

				super.state(context, uniqueReport, "ticker", "acme.validation.audit-report.duplicated-ticker.message");
			}
			{
				boolean correctMinimunSections;

				correctMinimunSections = auditReport.getDraftMode() || this.sectionRepository.findCountAuditReportsByAuditReportId(auditReport.getId()) >= 1;

				super.state(context, correctMinimunSections, "draftMode", "acme.validation.audit-report.minimun-sections.message");
			}
			{
				Date startMoment = auditReport.getStartMoment();
				Date endMoment = auditReport.getEndMoment();
				boolean correctMoments;
				boolean isStartFuture;
				boolean isEndFuture;
				boolean isValidInterval;

				if (!auditReport.getDraftMode() && startMoment != null && endMoment != null) {
					isStartFuture = MomentHelper.isFuture(startMoment);
					isEndFuture = MomentHelper.isFuture(endMoment);
					isValidInterval = MomentHelper.isAfter(endMoment, startMoment);
					correctMoments = isStartFuture && isEndFuture && isValidInterval;
					super.state(context, correctMoments, "endMoment", "acme.validation.audit-report.moments.message");
				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}
}

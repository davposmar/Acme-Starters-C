
package acme.features.audits;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditSectionRepository extends AbstractRepository {

	@Query("Select SUM(a.hours) from AuditSection a where a.auditReport.id = :auditReportId")
	Integer sumHoursByAuditReportId(int auditReportId);

	@Query("Select COUNT(a) from AuditSection a where a.auditReport.id = :auditReportId")
	Integer findCountAuditReportsByAuditReportId(int auditReportId);
}


package acme.features.audits;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditSectionRepository extends AbstractRepository {

	@Query("Select SUM(as.hours) from AuditSection as where as.auditReport.id = :auditReportId")
	Integer sumHoursByAuditReportId(int auditReportId);
}

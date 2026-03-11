
package acme.features.audits;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditReport;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("Select ar from AuditReport ar where ar.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);
}

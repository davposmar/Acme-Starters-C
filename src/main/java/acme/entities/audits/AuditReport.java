
package acme.entities.audits;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AuditReport extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	// @ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	// @ValidHeader
	@Column
	private String				name;

	@Mandatory
	// @ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Column
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Column
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------


	//@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		Long startTime = this.getStartMoment().getTime();
		Long endTime = this.getEndMoment().getTime();

		return (endTime - startTime) / (1000.0 * 60 * 60 * 24 * 30);
	}

	//@Mandatory
	//@ValidNumber(min = 0)
	@Transient
	public Integer getHours() {
		// TODO
		return null;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor auditor;
}

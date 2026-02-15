
package acme.entities.inventions;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invention extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	// TODO: @ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	// TODO: @ValidHeader
	@Column
	private String				name;

	@Mandatory
	// TODO: @ValidText
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment; // 	@Temporal(TemporalType.TIMESTAMP) don't support Moment datatype. ??

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment; // 	@Temporal(TemporalType.TIMESTAMP) don't support Moment datatype. ??

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derived attributes -----------------------------------------------------´

	@Transient
	@Autowired
	private InventionRepository	repository;


	//@Mandatory ?
	@Valid
	@Transient
	public Double getMonthsActive() {
		return (double) ChronoUnit.MONTHS.between(this.getStartMoment().toInstant(), this.getEndMoment().toInstant());
	}
	//@Mandatory ?
	//@ValidMoney(min = 0) ?
	@Transient
	public Money getCost() {
		Money cost = new Money();
		Double amount = this.repository.computeCostOfInvention(this.getId());
		amount = amount == null ? 0.0 : amount;
		cost.setAmount(amount);
		cost.setCurrency("EUR");
		return cost;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor inventor;
}

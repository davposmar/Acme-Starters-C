
package acme.entities.inventions;

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
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.inventor.invention.InventionRepository;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
public class Invention extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
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


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;

		long diff = this.endMoment.getTime() - this.startMoment.getTime();
		double month = diff / (1000.0 * 60 * 60 * 24 * 30);
		return Math.round(month * 10.0) / 10.0;
	}
	@Mandatory
	@ValidMoney(min = 0.0, max = 999999999999999999999999999.0)
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


package acme.entities.fundraising;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidStrategy
public class Strategy extends AbstractEntity {

	// Serialisation version -----------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ----------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column(unique = true)
	private String				name;

	@Mandatory
	@ValidText
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	@ManyToOne
	@JoinColumn(name = "fundraiser_id")
	private Fundraiser			fundraiser;

	// Derived attributes --------------------------------------------

	@Mandatory
	@Valid
	@Transient
	@Autowired
	private StrategyRepository	strategyRepository;


	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		long days = ChronoUnit.DAYS.between(this.startMoment.toInstant(), this.endMoment.toInstant());
		double months = days / 30.436875;
		return Math.round(months * 10.0) / 10.0;
	}

	@Mandatory
	@ValidScore
	@Transient
	public Double getExpectedPercentage() {
		Double total = this.strategyRepository.expectedPercentage(this.getId());
		return total == null ? 0 : total;
	}

}

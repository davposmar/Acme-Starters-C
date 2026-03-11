
package acme.entities.sponsorships;

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
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidSponsorship;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.donation.SponsorshipRepository;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@ValidSponsorship
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String					name;

	@Mandatory
	@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					endMoment;

	@Optional
	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	// Derived attributes -----------------------------------------------------

	@Mandatory
	@Valid
	@Transient
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.getStartMoment() == null || this.getEndMoment() == null)
			return 0.0;
		double months = MomentHelper.computeDifference(this.getStartMoment(), this.getEndMoment(), ChronoUnit.MONTHS);
		return Math.round(months * 10.0) / 10.0;
	}

	@Mandatory
	@ValidMoney(min = 0.0, max = 999999999999999999999999999.0)
	@Transient
	public Money getTotalMoney() {
		Money totalMoney = new Money();
		Double sum = this.sponsorshipRepository.calcTotalMoneySponsorship(this.getId());
		sum = sum == null ? 0.0 : sum;
		totalMoney.setAmount(sum);
		totalMoney.setCurrency("EUR");
		return totalMoney;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor sponsor;

}

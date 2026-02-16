
package acme.entities.sponsorships;

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
import acme.features.donation.DonationRepository;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	//@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	//@ValidHeader
	@Column
	private String				name;

	@Mandatory
	//@ValidHeader
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				starMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
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

	@Transient
	@Autowired
	private DonationRepository	donationRepository;


	@Transient
	public Double getMonthsActive() {
		if (this.getStarMoment() == null || this.getEndMoment() == null)
			return 0.0;
		return (double) ((this.getEndMoment().getTime() - this.getStarMoment().getTime()) / (1000 * 60 * 60 * 24 * 30));
	}

	@Transient
	public Money getTotalMoney() {
		Money totalMoney = new Money();
		Double sum = this.donationRepository.calcTotalMoneySponsorship(this.getId());
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

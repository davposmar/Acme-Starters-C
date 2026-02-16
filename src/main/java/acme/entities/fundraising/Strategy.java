
package acme.entities.fundraising;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Strategy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long			id;

	@NotBlank
	private String			ticker;

	@NotBlank
	private String			name;

	@NotBlank
	private String			description;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date			startMoment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date			endMoment;

	@URL
	private String			moreInfo;

	@NotNull
	private Boolean			draftMode;

	@ManyToOne
	@JoinColumn(name = "fundraiser_id")
	private Fundraiser		fundraiser;

	@OneToMany
	private List<Tactic>	tactics	= new ArrayList<>();


	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		long days = ChronoUnit.DAYS.between(this.startMoment.toInstant(), this.endMoment.toInstant());
		double months = days / 30.436875;
		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Double getExpectedPercentage() {
		if (this.tactics == null || this.tactics.isEmpty())
			return 0.0;
		return this.tactics.stream().filter(t -> t.getExpectedPercentage() != null).mapToDouble(Tactic::getExpectedPercentage).sum();
	}

}

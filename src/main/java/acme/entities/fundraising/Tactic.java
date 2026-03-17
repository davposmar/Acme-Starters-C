
package acme.entities.fundraising;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidScore;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tactic extends AbstractEntity {

	// Serialisation version -----------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ----------------------------------------------------

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	private String				notes;

	@Mandatory
	@ValidScore
	@Column
	private Double				expectedPercentage;

	@Mandatory
	@Valid
	@Column
	@Enumerated(EnumType.STRING)
	private TacticKind			kind;

	@ManyToOne
	@JoinColumn(name = "strategy_id", nullable = false)
	private Strategy			strategy;

}

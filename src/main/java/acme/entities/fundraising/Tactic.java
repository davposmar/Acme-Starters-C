
package acme.entities.fundraising;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tactic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long		id;

	@NotBlank
	private String		name;

	@NotBlank
	private String		notes;

	@NotNull
	private Double		expectedPercentage;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TacticKind	kind;

	@ManyToOne
	@JoinColumn(name = "strategy_id", nullable = false)
	private Strategy	strategy;

}

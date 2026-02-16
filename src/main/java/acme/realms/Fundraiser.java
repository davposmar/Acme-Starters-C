
package acme.realms;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.entities.fundraising.Strategy;

@Entity
public class Fundraiser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long						id;

	@NotBlank
	private String						bank;

	@NotBlank
	private String						statement;

	@NotNull
	private Boolean						agent;

	@OneToMany
	private java.util.List<Strategy>	strategies;

}

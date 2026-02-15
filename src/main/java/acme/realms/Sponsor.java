
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends AbstractRole {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	//@ValidText
	@Column
	private String				address;

	@Mandatory
	//@ValidText
	@Column
	private String				im;

	@Mandatory
	//@ValidText
	@Column
	private Boolean				gold;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}

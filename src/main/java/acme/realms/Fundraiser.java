
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Fundraiser extends AbstractRole {

	@Mandatory
	// TODO: @ValidHeader
	@Column
	private String	bank;

	@Mandatory
	// TODO: @ValidText
	@Column
	private String	statement;

	@Mandatory
	@Valid
	@Column
	private Boolean	agent;

}

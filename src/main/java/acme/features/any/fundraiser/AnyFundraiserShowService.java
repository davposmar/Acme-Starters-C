
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	@Autowired
	private AnyFundraiserRepository	repository;

	private Fundraiser				fundraiser;


	@Override
	public void authorise() {
		boolean status;

		status = this.fundraiser != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "identity.fullName", "bank", "statement", "agent", "identity.email");
	}
}

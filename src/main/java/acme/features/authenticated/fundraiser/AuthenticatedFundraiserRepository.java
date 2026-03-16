
package acme.features.authenticated.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Fundraiser;

@Repository
public interface AuthenticatedFundraiserRepository extends AbstractRepository {

	@Query("Select f from Fundraiser f where f.userAccount.id = :id")
	Fundraiser findOneFundraiserByUserAccountId(int id);

	@Query("Select u from UserAccount u where u.id = :id")
	UserAccount findOneUserAccountById(int id);
}

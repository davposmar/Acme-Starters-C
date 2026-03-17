/*
 * AnyDonationRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface AnyDonationRepository extends AbstractRepository {

	@Query("select d from Donation d where d.id = :donationId")
	Donation findDonationById(int donationId);

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("select ss from Sponsorship ss where ss.id = :sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

}

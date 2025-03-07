package com.gym.management.membership.service;

import java.time.LocalDate;
import java.util.List;

import com.gym.management.membership.model.Membership;

/**
 * Service interface for managing memberships.
 */
public interface MembershipService {

	/**
	 * Creates a new membership for a member.
	 */
	Membership createMembership(int memberId, Membership.MembershipType type);

	/**
	 * Renews an existing membership.
	 */
	Membership renewMembership(int membershipId);

	/**
	 * Upgrades an existing membership.
	 */
	Membership upgradeMembership(int membershipId, Membership.MembershipType newType);

	/**
	 * Deactivates a membership.
	 */
	void deactivateMembership(int membershipId);

	/**
	 * Retrieves all memberships.
	 */
	List<Membership> getAllMemberships();

	/**
	 * Retrieves a membership by member ID.
	 */
	Membership getMembership(int memberId);

    /**
     * Calculates the end date of a membership based on its type.
     */
	LocalDate calculateEndDate(Membership.MembershipType type);
}

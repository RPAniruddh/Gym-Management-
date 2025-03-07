package com.gym.management.membership.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.management.membership.exception.MembershipAlreadyExistsException;
import com.gym.management.membership.exception.ResourceNotFoundException;
import com.gym.management.membership.model.Member;
import com.gym.management.membership.model.Membership;
import com.gym.management.membership.repository.MembershipRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing gym memberships. This class provides
 * methods to create, renew, upgrade, and deactivate memberships. It uses the
 * MembershipRepository for database operations.
 */
@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {
	private final MemberService memberService;
	private final MembershipRepository membershipRepository;

	private static final String MEMBERSHIP_NOT_FOUND_MESSAGE = "Membership not found for member with ID";

	/**
	 * Creates a new membership for a member.
	 *
	 * @param memberId the ID of the member
	 * @param type     the type of membership
	 * @return the created membership
	 * @throws MembershipAlreadyExistsException if the member already has an active
	 *                                          membership
	 */
	@Transactional
	@Override
	public Membership createMembership(int memberId, Membership.MembershipType type) {
		Member member = memberService.getMember(memberId);

		if (member.getMembership() != null) {
			throw new MembershipAlreadyExistsException("Member already has an active membership");
		}

		Membership membership = new Membership();
		membership.setMember(member);
		membership.setMembershipType(type);
		membership.setStatus(Membership.MembershipStatus.ACTIVE);
		membership.setStartDate(LocalDate.now());
		membership.setEndDate(calculateEndDate(type));

		member.setMembership(membership);
		memberService.createMember(member);

		return membership;
	}

	/**
	 * Renews an existing membership.
	 *
	 * @param membershipId the ID of the membership to renew
	 * @return the renewed membership
	 * @throws ResourceNotFoundException if no membership is found with the given ID
	 */
	@Transactional
	@Override
	public Membership renewMembership(int membershipId) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		if (membership == null) {
			throw new ResourceNotFoundException(MEMBERSHIP_NOT_FOUND_MESSAGE + membershipId);
		}

		membership.setStartDate(LocalDate.now());
		membership.setEndDate(calculateEndDate(membership.getMembershipType()));
		membership.setStatus(Membership.MembershipStatus.ACTIVE);

		memberService.createMember(member);
		return membership;
	}

	/**
	 * Upgrades an existing membership to a new type.
	 *
	 * @param membershipId the ID of the membership to upgrade
	 * @param newType      the new type of membership
	 * @return the upgraded membership
	 * @throws ResourceNotFoundException if no membership is found with the given ID
	 */
	@Transactional
	@Override
	public Membership upgradeMembership(int membershipId, Membership.MembershipType newType) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		if (membership == null) {
			throw new ResourceNotFoundException(MEMBERSHIP_NOT_FOUND_MESSAGE + membershipId);
		}

		membership.setMembershipType(newType);
		membership.setEndDate(calculateEndDate(newType));

		memberService.createMember(member);
		return membership;
	}

	/**
	 * Deactivates an existing membership.
	 *
	 * @param membershipId the ID of the membership to deactivate
	 * @throws ResourceNotFoundException if no membership is found with the given ID
	 */
	@Transactional
	@Override
	public void deactivateMembership(int membershipId) {
		Member member = memberService.getMember(membershipId);
		Membership membership = member.getMembership();

		if (membership == null) {
			throw new ResourceNotFoundException(MEMBERSHIP_NOT_FOUND_MESSAGE + membershipId);
		}

		membership.setStatus(Membership.MembershipStatus.INACTIVE);
		memberService.createMember(member);
	}

	/**
	 * Calculates the end date of a membership based on its type.
	 *
	 * @param type the type of membership
	 * @return the calculated end date
	 */
	@Override
	public LocalDate calculateEndDate(Membership.MembershipType type) {
		return switch (type) {
		case BASIC -> LocalDate.now().plusMonths(1);
		case PREMIUM -> LocalDate.now().plusMonths(3);
		};
	}

	/**
	 * Retrieves all memberships from the database.
	 *
	 * @return a list of all memberships
	 */
	@Override
	public List<Membership> getAllMemberships() {
		return membershipRepository.findAll();
	}

	/**
	 * Retrieves a membership by the member ID.
	 *
	 * @param memberId the ID of the member
	 * @return the retrieved membership
	 * @throws ResourceNotFoundException if no membership is found with the given ID
	 */
	@Override
	public Membership getMembership(int memberId) {
		Optional<Membership> membershipOptional = membershipRepository.findById(memberId);

		if (membershipOptional.isPresent()) {
			return membershipOptional.get();
		} else {
			throw new ResourceNotFoundException("Membership not found with id: " + memberId);
		}
	}
}
package com.gym.management.membership.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gym.management.membership.exception.ResourceNotFoundException;
import com.gym.management.membership.model.Member;
import com.gym.management.membership.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing gym members. This class provides methods
 * to create, retrieve, update, and delete members. It uses the MemberRepository
 * for database operations.
 */
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository memberRepository;

	/**
	 * Creates a new member and saves it to the database.
	 *
	 * @param member the member to be created
	 * @return the created member
	 */
	@Override
	public Member createMember(Member member) {
		return memberRepository.save(member);
	}

	/**
	 * Retrieves a member by their ID.
	 *
	 * @param id the ID of the member to retrieve
	 * @return the retrieved member
	 * @throws ResourceNotFoundException if no member is found with the given ID
	 */
	@Override
	public Member getMember(int id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Member not found with ID " + id));
	}

	/**
	 * Retrieves all members from the database.
	 *
	 * @return a list of all members
	 */
	@Override
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	/**
	 * Updates an existing member's details. This method is transactional to ensure
	 * data consistency.
	 *
	 * @param id            the ID of the member to update
	 * @param memberDetails the new details of the member
	 * @return the updated member
	 */
	@Override
	@Transactional
	public Member updateMember(int id, Member memberDetails) {
		Member member = getMember(id);
		member.setFirstName(memberDetails.getFirstName());
		member.setLastName(memberDetails.getLastName());
		member.setEmail(memberDetails.getEmail());
		member.setPhoneNumber(memberDetails.getPhoneNumber());
		member.setDateOfBirth(memberDetails.getDateOfBirth());
		return memberRepository.save(member);
	}

	/**
	 * Deletes a member by their ID.
	 *
	 * @param id the ID of the member to delete
	 * @throws ResourceNotFoundException if no member is found with the given ID
	 */
	@Override
	public void deleteMember(int id) {
		if (!memberRepository.existsById(id)) {
			throw new ResourceNotFoundException("Member not found with ID " + id);
		}
		memberRepository.deleteById(id);
	}
}
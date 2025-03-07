package com.gym.management.membership.service;

import java.util.List;

import com.gym.management.membership.model.Member;

/**
 * Service interface for managing members.
 */
public interface MemberService {

	/**
	 * Creates a new member.
	 */
	Member createMember(Member member);

	/**
	 * Retrieves a member by ID.
	 */
	Member getMember(int id);

	/**
	 * Retrieves all members.
	 */
	List<Member> getAllMembers();

	/**
	 * Updates an existing member.
	 */
	Member updateMember(int id, Member memberDetails);

	/**
	 * Deletes a member by ID.
	 */
	void deleteMember(int id);

}

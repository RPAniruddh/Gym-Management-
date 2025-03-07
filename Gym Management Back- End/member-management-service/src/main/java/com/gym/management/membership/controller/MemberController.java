package com.gym.management.membership.controller;

import com.gym.management.membership.model.Member;
import com.gym.management.membership.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing member-related operations. This class provides
 * endpoints for creating, retrieving, updating, and deleting members.
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	/**
	 * Endpoint to create a new member.
	 * 
	 * @param member The member entity to be created.
	 * @return ResponseEntity containing the created member.
	 */
	@PostMapping("/add")
	public ResponseEntity<Member> createMember(@RequestBody Member member) {
		return ResponseEntity.ok(memberService.createMember(member));
	}

	/**
	 * Endpoint to retrieve a member by ID.
	 * 
	 * @param id The ID of the member to be retrieved.
	 * @return ResponseEntity containing the retrieved member.
	 */
	@GetMapping("/get/{id}")
	public ResponseEntity<Member> getMember(@PathVariable int id) {
		return ResponseEntity.ok(memberService.getMember(id));
	}

	/**
	 * Endpoint to retrieve all members.
	 * 
	 * @return ResponseEntity containing a list of all members.
	 */
	@GetMapping
	public ResponseEntity<List<Member>> getAllMembers() {
		return ResponseEntity.ok(memberService.getAllMembers());
	}

	/**
	 * Endpoint to update an existing member.
	 * 
	 * @param id     The ID of the member to be updated.
	 * @param member The member entity with updated information.
	 * @return ResponseEntity containing the updated member.
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<Member> updateMember(@PathVariable int id, @RequestBody Member member) {
		return ResponseEntity.ok(memberService.updateMember(id, member));
	}

	/**
	 * Endpoint to update an existing member.
	 * 
	 * @param id     The ID of the member to be updated.
	 * @param member The member entity with updated information.
	 * @return ResponseEntity containing the updated member.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable int id) {
		memberService.deleteMember(id);
		return ResponseEntity.ok().build();
	}
}
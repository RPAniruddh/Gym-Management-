package com.gym.management.membership.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gym.management.membership.model.Member;
import com.gym.management.membership.service.JwtService;
import com.gym.management.membership.service.MemberService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing member-related operations. This class provides
 * endpoints for creating, retrieving, updating, and deleting members.
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final JwtService jwtService;
	
	@Autowired
    private RestTemplate restTemplate;

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
	 * Endpoint to retrieve a member by Email.
	 * 
	 * @param email The Email of the member to be retrieved.
	 * @return ResponseEntity containing the retrieved member.
	 */
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<Member> getMember(@PathVariable String email) {
		return ResponseEntity.ok(memberService.getMemberByEmail(email));
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
	 * Endpoint to delete a member.
	 * 
	 * @param id     The ID of the member to be deleted.
	 * @param member The member entity with updated information.
	 * @return ResponseEntity containing the updated member.
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteMember(@PathVariable int id) {
	    memberService.deleteMember(id);

	    String fitnessServiceUrl = "http://localhost:1235/fitness/workouts/member/" + id;

	    HttpHeaders headers = new HttpHeaders();
	    String token = jwtService.getToken();  // Get JWT token
	    headers.set("Authorization", "Bearer " + token);  // Set JWT in the header
	    headers.set("X-Internal-Request", "true"); // Optional custom header

	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    restTemplate.exchange(fitnessServiceUrl, HttpMethod.DELETE, entity, Void.class);

	    return ResponseEntity.ok().build();
	}
}
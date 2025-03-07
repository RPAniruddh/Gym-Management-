package com.gym.management.membership.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gym.management.membership.model.Membership;
import com.gym.management.membership.service.MembershipService;

import lombok.RequiredArgsConstructor;

/*
 * REST controller for managing membership-related operations.
 * This class provides endpoints for creating, renewing, upgrading, deactivating
 */
@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {
	private final MembershipService membershipService;

	/**
	 * Endpoint to create a new membership for a member.
	 * 
	 * @param memberId The ID of the member.
	 * @param type     The type of membership.
	 * @return ResponseEntity containing the created membership.
	 */
	@PostMapping("/{memberId}")
	public ResponseEntity<Membership> createMembership(@PathVariable int memberId,
			@RequestParam Membership.MembershipType type) {
		return ResponseEntity.ok(membershipService.createMembership(memberId, type));
	}

	/**
	 * Endpoint to renew an existing membership.
	 * 
	 * @param membershipId The ID of the membership to be renewed.
	 * @return ResponseEntity containing the renewed membership.
	 */
	@PutMapping("/{membershipId}/renew")
	public ResponseEntity<Membership> renewMembership(@PathVariable int membershipId) {
		return ResponseEntity.ok(membershipService.renewMembership(membershipId));
	}

	/**
	 * Endpoint to upgrade an existing membership.
	 * 
	 * @param membershipId The ID of the membership to be upgraded.
	 * @param newType      The new type of membership.
	 * @return ResponseEntity containing the upgraded membership.
	 */
	@PutMapping("/{membershipId}/upgrade")
	public ResponseEntity<Membership> upgradeMembership(@PathVariable int membershipId,
			@RequestParam Membership.MembershipType newType) {
		return ResponseEntity.ok(membershipService.upgradeMembership(membershipId, newType));
	}

	/**
	 * Endpoint to deactivate a membership.
	 * 
	 * @param membershipId The ID of the membership to be deactivated.
	 * @return ResponseEntity with no content.
	 */
	@PostMapping("/{membershipId}/deactivate")
	public ResponseEntity<Void> deactivateMembership(@PathVariable int membershipId) {
		membershipService.deactivateMembership(membershipId);
		return ResponseEntity.ok().build();
	}

	/**
	 * Endpoint to retrieve a membership by ID.
	 * 
	 * @param membershipId The ID of the membership to be retrieved.
	 * @return ResponseEntity containing the retrieved membership.
	 */
	@GetMapping("/{membershipId}")
	public ResponseEntity<Membership> getMembershipById(@PathVariable int membershipId) {
		return ResponseEntity.ok(membershipService.getMembership(membershipId));
	}

	/**
	 * Endpoint to retrieve all memberships.
	 * 
	 * @return ResponseEntity containing a list of all memberships.
	 */
	@GetMapping
	public ResponseEntity<List<Membership>> getAllMemberships() {
		return ResponseEntity.ok(membershipService.getAllMemberships());
	}
}
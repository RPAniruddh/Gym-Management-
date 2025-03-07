package com.gym.management.membership.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "memberships")
public class Membership {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "Member is mandatory")
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@NotNull(message = "Membership type is mandatory")
	private MembershipType membershipType;

	@NotNull(message = "Membership status is mandatory")
	@Enumerated(EnumType.STRING)
	private MembershipStatus status;

	@NotNull(message = "Start date is mandatory")
	private LocalDate startDate;

	@NotNull(message = "End date is mandatory")
	private LocalDate endDate;

	private LocalDateTime createdAt = LocalDateTime.now();

	public enum MembershipType {
		BASIC, PREMIUM
	}

	public enum MembershipStatus {
		ACTIVE, INACTIVE
	}

	@Override
	public String toString() {
		return "Membership{" + "id=" + id + ", membershipType=" + membershipType + ", status=" + status + ", startDate="
				+ startDate + ", endDate=" + endDate;
	}
}
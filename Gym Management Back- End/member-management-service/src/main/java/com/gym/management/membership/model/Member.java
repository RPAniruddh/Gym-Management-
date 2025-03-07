package com.gym.management.membership.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "members")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "First name is mandatory")
	@Size(max = 10, message = "First name must be less than 50 characters")
	@Column(nullable = false)
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	@Size(max = 10, message = "Last name must be less than 50 characters")
	@Column(nullable = false)
	private String lastName;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Phone number is mandatory")
	@Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
	@Column(nullable = false)
	private String phoneNumber;

	@NotNull(message = "Date of birth is mandatory")
	@Past(message = "Date of birth must be a past date")
	@Column(nullable = false)
	private Date dateOfBirth;

	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
	@JsonIgnore
	private Membership membership;
}
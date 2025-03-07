package com.gym.management.membership.exception;

public class MembershipAlreadyExistsException extends RuntimeException {
	public MembershipAlreadyExistsException(String message) {
		super(message);
	}
}
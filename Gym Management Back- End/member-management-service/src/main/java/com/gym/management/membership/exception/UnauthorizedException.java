package com.gym.management.membership.exception;

public class UnauthorizedException extends RuntimeException {
	 
	public UnauthorizedException(String message)
	{
		super(message);
	}
}
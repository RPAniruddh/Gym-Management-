package com.gym.management.bookingservice.service;

import java.util.List;

import com.gym.management.bookingservice.model.Booking;
import com.gym.management.bookingservice.model.BookingStatus;

/**
 * Service interface for managing bookings.
 */
public interface BookingService {

	/**
	 * Creates a new booking.
	 */
	Booking createBooking(int memberId, int classId);

	/**
	 * Retrieves bookings for a member.
	 */
	List<Booking> getMemberBookings(int memberId);

	/**
	 * Updates booking status.
	 */
	Booking updateBookingStatus(int bookingId, BookingStatus status);

	/**
	 * Deletes a booking.
	 */
	void deleteBooking(int id);
}
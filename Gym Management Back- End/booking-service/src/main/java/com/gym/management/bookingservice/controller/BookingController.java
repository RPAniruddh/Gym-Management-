package com.gym.management.bookingservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gym.management.bookingservice.model.Booking;
import com.gym.management.bookingservice.model.BookingStatus;
import com.gym.management.bookingservice.service.BookingService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing booking-related operations. Provides endpoints
 * for creating, retrieving, updating, and deleting workouts and exercises.
 */
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
	private final BookingService bookingService;

	/**
	 * Creates a new booking for a gym class.
	 *
	 * @param memberId the ID of the member making the booking
	 * @param classId  the ID of the class to be booked
	 * @return ResponseEntity containing the created Booking object
	 */
	@PostMapping
	public ResponseEntity<Booking> createBooking(@RequestParam int memberId, @RequestParam int classId) {
		return ResponseEntity.ok(bookingService.createBooking(memberId, classId));
	}

	/**
	 * Retrieves all bookings for a specific member.
	 *
	 * @param memberId the ID of the member whose bookings are to be retrieved
	 * @return ResponseEntity containing a list of Booking objects
	 */
	@GetMapping("/member/{memberId}")
	public ResponseEntity<List<Booking>> getMemberBookings(@PathVariable int memberId) {
		return ResponseEntity.ok(bookingService.getMemberBookings(memberId));
	}

	/**
	 * Updates the status of an existing booking.
	 *
	 * @param id     the ID of the booking to be updated
	 * @param status the new status of the booking
	 * @return ResponseEntity containing the updated Booking object
	 */
	@PatchMapping("/{id}/status")
	public ResponseEntity<Booking> updateBookingStatus(@PathVariable int id, @RequestParam BookingStatus status) {
		return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
	}

	/**
	 * Deletes an existing booking.
	 *
	 * @param id the ID of the booking to be deleted
	 * @return ResponseEntity with no content
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBooking(@PathVariable int id) {
		bookingService.deleteBooking(id);
		return ResponseEntity.noContent().build();
	}
}
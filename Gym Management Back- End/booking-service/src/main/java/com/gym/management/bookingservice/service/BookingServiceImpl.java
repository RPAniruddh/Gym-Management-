package com.gym.management.bookingservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gym.management.bookingservice.dto.MemberDTO;
import com.gym.management.bookingservice.exceptions.ResourceNotFoundException;
import com.gym.management.bookingservice.model.Booking;
import com.gym.management.bookingservice.model.BookingStatus;
import com.gym.management.bookingservice.model.Class;
import com.gym.management.bookingservice.repository.BookingRepository;
import com.gym.management.bookingservice.repository.ClassRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing bookings. Provides methods for creating,
 * retrieving, updating, and deleting bookings.
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
	private final BookingRepository bookingRepository;
	private final ClassRepository classRepository;
	private final RestTemplate restTemplate;

	private static final String MEMBER_SERVICE_URL = "http://localhost:8082/members/get/";

	/**
	 * Creates a new booking for a gym class.
	 *
	 * @param memberId the ID of the member making the booking
	 * @param classId  the ID of the class to be booked
	 * @return the created Booking object
	 * @throws ResourceNotFoundException if the member or class is not found, or if
	 *                                   the class is full
	 */
	@Override
	public Booking createBooking(int memberId, int classId) {
		MemberDTO member = restTemplate.getForObject(MEMBER_SERVICE_URL + memberId, MemberDTO.class);

		if (member == null) {
			throw new ResourceNotFoundException("Member not found for ID " + memberId);
		}

		Class classEntity = classRepository.findById(classId)
				.orElseThrow(() -> new RuntimeException("Class not found"));

		// Check if class is full
		int currentBookings = (int) bookingRepository.findByClassDetailsId(classId).stream()
				.filter(b -> b.getStatus() != BookingStatus.CANCELLED).count();

		if (currentBookings >= classEntity.getCapacity()) {
			throw new ResourceNotFoundException("Class is full for ID " + classId);
		}

		Booking booking = new Booking();
		booking.setClassDetails(classEntity);
		booking.setMemberId(memberId);
		booking.setBookingTime(LocalDateTime.now());
		booking.setStatus(BookingStatus.PENDING);

		return bookingRepository.save(booking);
	}

	/**
	 * Retrieves all bookings for a specific member.
	 *
	 * @param memberId the ID of the member whose bookings are to be retrieved
	 * @return a list of Booking objects
	 * @throws ResourceNotFoundException if the member is not found
	 */
	@Override
	public List<Booking> getMemberBookings(int memberId) {
		MemberDTO member = restTemplate.getForObject(MEMBER_SERVICE_URL + memberId, MemberDTO.class);

		if (member == null) {
			throw new ResourceNotFoundException("Member not found for ID " + memberId);
		}

		return bookingRepository.findByMemberId(memberId);
	}

	/**
	 * Updates the status of an existing booking.
	 *
	 * @param bookingId the ID of the booking to be updated
	 * @param status    the new status of the booking
	 * @return the updated Booking object
	 * @throws ResourceNotFoundException if the booking is not found
	 */
	@Override
	public Booking updateBookingStatus(int bookingId, BookingStatus status) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found for ID " + bookingId));

		booking.setStatus(status);
		return bookingRepository.save(booking);
	}

	/**
	 * Deletes an existing booking.
	 *
	 * @param id the ID of the booking to be deleted
	 * @throws ResourceNotFoundException if the booking is not found
	 */
	@Override
	public void deleteBooking(int id) {
		if (!bookingRepository.existsById(id)) {
			throw new ResourceNotFoundException("Booking not found for ID " + id);
		}
		bookingRepository.deleteById(id);
	}
}
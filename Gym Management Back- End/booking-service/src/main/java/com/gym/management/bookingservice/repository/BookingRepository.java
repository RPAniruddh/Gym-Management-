package com.gym.management.bookingservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.management.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByClassDetailsId(int classId);
    List<Booking> findByMemberId(int memberId);

}

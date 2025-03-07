package com.gym.management.bookingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.gym.management.bookingservice.dto.MemberDTO;
import com.gym.management.bookingservice.exceptions.ResourceNotFoundException;
import com.gym.management.bookingservice.model.Booking;
import com.gym.management.bookingservice.model.Class;
import com.gym.management.bookingservice.model.BookingStatus;
import com.gym.management.bookingservice.repository.BookingRepository;
import com.gym.management.bookingservice.repository.ClassRepository;
import com.gym.management.bookingservice.service.BookingServiceImpl;


class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ClassRepository classRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private MemberDTO member;
    private Class classEntity;
    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        member = new MemberDTO();
        member.setId(1);

        classEntity = new Class();
        classEntity.setId(1);
        classEntity.setCapacity(10);

        booking = new Booking();
        booking.setId(1);
        booking.setMemberId(1);
        booking.setClassDetails(classEntity);
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);
    }


    @Test
    void testCreateBooking_MemberNotFound() {
        when(restTemplate.getForObject(anyString(), eq(MemberDTO.class))).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> bookingService.createBooking(1, 1));
    }

    @Test
    void testCreateBooking_ClassNotFound() {
        when(restTemplate.getForObject(anyString(), eq(MemberDTO.class))).thenReturn(member);
        when(classRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1, 1));
    }


    @Test
    void testGetMemberBookings_Success() {
        when(restTemplate.getForObject(anyString(), eq(MemberDTO.class))).thenReturn(member);
        when(bookingRepository.findByMemberId(anyInt())).thenReturn(Arrays.asList(booking));

        assertEquals(1, bookingService.getMemberBookings(1).size());
    }

    @Test
    void testGetMemberBookings_MemberNotFound() {
        when(restTemplate.getForObject(anyString(), eq(MemberDTO.class))).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> bookingService.getMemberBookings(1));
    }


    @Test
    void testUpdateBookingStatus_BookingNotFound() {
        when(bookingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.updateBookingStatus(1, BookingStatus.CONFIRMED));
    }

    @Test
    void testDeleteBooking_Success() {
        when(bookingRepository.existsById(anyInt())).thenReturn(true);

        bookingService.deleteBooking(1);

        verify(bookingRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteBooking_BookingNotFound() {
        when(bookingRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> bookingService.deleteBooking(1));
    }
}


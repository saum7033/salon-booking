package com.booking.demo.service;


import com.booking.demo.domain.BookingStatus;
import com.booking.demo.dto.BookingRequest;
import com.booking.demo.dto.SalonDTO;
import com.booking.demo.dto.ServiceDTO;
import com.booking.demo.dto.UserDTO;
import com.booking.demo.modal.Booking;
import com.booking.demo.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking, UserDTO ser, SalonDTO salon,
                          Set<ServiceDTO> serviceDTOSet);
    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsBySalon(Long salonId);
    Booking getBookingById(Long id) throws Exception;
    Booking updateBooking(Long bookingId, BookingStatus status);
    List<Booking> getBookingsByDate(LocalDate dae, Long salonId);
    SalonReport getSalonReport(Long salonId);
}

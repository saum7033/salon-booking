package com.booking.demo.service.impl;

import com.booking.demo.domain.BookingStatus;
import com.booking.demo.dto.BookingRequest;
import com.booking.demo.dto.SalonDTO;
import com.booking.demo.dto.ServiceDTO;
import com.booking.demo.dto.UserDTO;
import com.booking.demo.modal.Booking;
import com.booking.demo.modal.SalonReport;
import com.booking.demo.repository.BookingRepository;
import com.booking.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    @Override
    public Booking createBooking(BookingRequest booking, UserDTO user, SalonDTO salon, Set<ServiceDTO> serviceDTOSet)   {
        int totalDuration = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getDuration)
                .sum();
        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);
        Boolean isSlotAvailable = isTimeSlotAvailable(salon,bookingStartTime,bookingEndTime);
        int totalPrice = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getPrice)
                .sum();
        Set<Long> idList = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());
        Booking newBooking = new Booking();
        newBooking.setCustomerId(user.getId());
        newBooking.setSalonId(salon.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setSartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);
        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeSlotAvailable(SalonDTO salonDTO,LocalDateTime bookingStartTime,LocalDateTime bookingEndTime)   {
        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());
        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getClosedTime().atDate(bookingEndTime.toLocalDate());
        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime)){
            throw new RuntimeException("Booking time must be within salon's working hours");        }
        for(Booking existingBooking : existingBookings){
            LocalDateTime existingBookingStartTime = existingBooking.getSartTime();
            LocalDateTime existingBookingEndTime = existingBooking.getEndTime();
            if(bookingStartTime.isBefore(existingBookingStartTime) && bookingEndTime.isAfter(existingBookingEndTime)){
                throw new RuntimeException("slot not available, choose different time");            }
            if(bookingStartTime.isEqual(existingBookingStartTime) || bookingEndTime.isEqual(existingBookingEndTime)){
                throw new RuntimeException("slot not available, choose different time");            }
        }
        return true;
    }
    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id)   {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new RuntimeException("booking not found");        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        List<Booking> allBookings = getBookingsBySalon(salonId);
        if(date == null){
            return allBookings;
        }
        return allBookings.stream()
                .filter(booking -> isSameDate(booking.getSartTime(),date) ||
                        isSameDate(booking.getEndTime(),date))
                .collect(Collectors.toList());
    }
    private boolean isSameDate(LocalDateTime startTime,LocalDate date){
        return startTime.toLocalDate().isEqual(date);
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {
        List<Booking>  bookings = getBookingsBySalon(salonId);
        int totalEarnings = bookings.stream()
                .mapToInt(Booking::getTotalPrice)
                .sum();
        Integer totalBooking = bookings.size();
        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .collect(Collectors.toList());
        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();
        SalonReport report = new SalonReport();
        report.setSalonId(salonId);
        report.setCancelledBookings(cancelledBookings.size());
        report.setTotalBookings(totalEarnings);
        report.setTotalEarnings(totalEarnings);
        report.setTotalRefund(totalRefund);
        report.setTotalBookings(totalBooking);
        return report;
    }
}

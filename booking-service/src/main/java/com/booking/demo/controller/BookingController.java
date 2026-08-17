package com.booking.demo.controller;

import com.booking.demo.domain.BookingStatus;
import com.booking.demo.dto.*;
import com.booking.demo.mapper.BookingMapper;
import com.booking.demo.modal.Booking;
import com.booking.demo.modal.SalonReport;
import com.booking.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestParam Long salonId,
            @RequestBody BookingRequest bookingRequest
            ) throws Exception{
        UserDTO user = new UserDTO();
        user.setId(1L);
        SalonDTO salon = new SalonDTO();
        salon.setId(salonId);
        salon.setOpenTime(LocalTime.of(9, 0));
        salon.setClosedTime(LocalTime.of(21, 0));
        Set<ServiceDTO> serviceDTOSet = new HashSet<>();
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(1L);
        serviceDTO.setPrice(399);
        serviceDTO.setDuration(45);
        serviceDTO.setName("Hair cut for women");
        serviceDTOSet.add(serviceDTO);
        Booking booking = bookingService.createBooking(bookingRequest,user,salon,serviceDTOSet);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(){
        List<Booking> bookings = bookingService.getBookingsByCustomer(1L);
        return ResponseEntity.ok(getBookingsDTOs(bookings));
    }

    private  Set<BookingDTO> getBookingsDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking->{
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(){
        List<Booking> bookings = bookingService.getBookingsBySalon(1L);
        return ResponseEntity.ok(getBookingsDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long bookingId
    ) throws Exception{
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus bookingStatus
            )throws Exception{
        Booking booking = bookingService.updateBooking(bookingId,bookingStatus);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(
            @PathVariable Long salonId,
            @RequestParam(required = false) LocalDate date
            ){
        List<Booking> bookings = bookingService.getBookingsByDate(date,salonId);
        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getSartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport()throws Exception{
        SalonReport salonReport = bookingService.getSalonReport(1L);
        return ResponseEntity.ok(salonReport);
    }
}

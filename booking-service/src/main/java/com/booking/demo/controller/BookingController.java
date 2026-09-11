package com.booking.demo.controller;

import com.booking.demo.domain.BookingStatus;
import com.booking.demo.domain.PaymentMethod;
import com.booking.demo.dto.*;
import com.booking.demo.mapper.BookingMapper;
import com.booking.demo.modal.Booking;
import com.booking.demo.modal.SalonReport;
import com.booking.demo.service.BookingService;
import com.booking.demo.service.client.PaymentFeignClient;
import com.booking.demo.service.client.ServiceOfferingFeignClient;
import com.booking.demo.service.client.SalonFeignClient;
import com.booking.demo.service.client.UserFeignClient;
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
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(
            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization")String jwt
            ) throws Exception{
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Set<ServiceDTO> serviceDTOSet = serviceOfferingFeignClient.getServicesByIds(bookingRequest.getServiceIds()).getBody();
        if(serviceDTOSet.isEmpty()){
            throw new Exception("service not found..");
        }
        Booking booking = bookingService.createBooking(bookingRequest,user,salon,serviceDTOSet);
        BookingDTO bookingDTO = BookingMapper.toDTO(booking);
        PaymentLinkResponse res = paymentFeignClient.createPaymentLink(bookingDTO,paymentMethod,jwt).getBody();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(@RequestHeader("Authorization")String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        if(user==null || user.getId()==null){
            throw new Exception("user not found from jwt..,");
        }
        List<Booking> bookings = bookingService.getBookingsByCustomer(user.getId());
        return ResponseEntity.ok(getBookingsDTOs(bookings));
    }

    private  Set<BookingDTO> getBookingsDTOs(List<Booking> bookings){
        return bookings.stream()
                .map(booking->{
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(@RequestHeader("Authorization")String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(user.getId()).getBody();
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());
        return ResponseEntity.ok(getBookingsDTOs(bookings));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long bookingId
    ) throws Exception{
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PatchMapping("/{bookingId}/status")
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
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(@RequestHeader("Authorization")String jwt)throws Exception{
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(user.getId()).getBody();
        SalonReport salonReport = bookingService.getSalonReport(salonDTO.getId());
        return ResponseEntity.ok(salonReport);
    }
}

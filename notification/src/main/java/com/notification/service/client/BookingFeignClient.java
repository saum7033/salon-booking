package com.notification.service.client;

import com.notification.payload.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("BOOKING-SERVICE")
public interface BookingFeignClient {

    @GetMapping("/api/bookings/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId)throws Exception;
}

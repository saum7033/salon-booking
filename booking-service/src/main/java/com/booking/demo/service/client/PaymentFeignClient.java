package com.booking.demo.service.client;

import com.booking.demo.domain.PaymentMethod;
import com.booking.demo.dto.BookingDTO;
import com.booking.demo.dto.PaymentLinkResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("payment")
public interface PaymentFeignClient {
    @PostMapping("/api/payments/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod, @RequestHeader("Authorization")String jwt);
}

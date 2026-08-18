package com.example.payment.controller;

import com.example.payment.domain.PaymentMethod;
import com.example.payment.modal.PaymentOrder;
import com.example.payment.payload.dto.BookingDTO;
import com.example.payment.payload.dto.UserDTO;
import com.example.payment.payload.response.PaymentLinkResponse;
import com.example.payment.service.PaymentService;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod) throws RazorpayException, StripeException {
        UserDTO user = new UserDTO();
        user.setFullName("Saumya");
        user.setEmail("rajsaumya7033@gmail.com");
        user.setId(1L);
        PaymentLinkResponse res = paymentService.createOrder(user,booking,paymentMethod);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws RazorpayException {
        PaymentOrder paymentOrder = paymentService.getPaymentByOrderByPaymentId(paymentLinkId);

        Boolean res = paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public PaymentOrder getPaymentByOrderId(@PathVariable Long id) throws Exception {
        return paymentService.getPaymentByOrderId(id);
    }
}

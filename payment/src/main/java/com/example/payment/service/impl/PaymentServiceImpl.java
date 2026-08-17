package com.example.payment.service.impl;

import com.example.payment.domain.PaymentMethod;
import com.example.payment.modal.PaymentOrder;
import com.example.payment.payload.dto.BookingDTO;
import com.example.payment.payload.dto.UserDTO;
import com.example.payment.payload.response.PaymentLinkResponse;
import com.example.payment.repository.PaymentOrderRepository;
import com.example.payment.service.PaymentService;
import com.razorpay.PaymentLink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentOrderRepository paymentOrderRepository;
    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) {
        return null;
    }

    @Override
    public PaymentOrder getPaymentByOrderId(Long id) {
        return null;
    }

    @Override
    public PaymentOrder getPaymentByOrderByPaymentId(String paymentId) {
        return null;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) {
        return null;
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) {
        return "";
    }
}

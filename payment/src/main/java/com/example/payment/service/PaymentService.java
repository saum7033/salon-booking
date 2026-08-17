package com.example.payment.service;


import com.example.payment.domain.PaymentMethod;
import com.example.payment.modal.PaymentOrder;
import com.example.payment.payload.dto.BookingDTO;
import com.example.payment.payload.dto.UserDTO;
import com.example.payment.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;

public interface PaymentService {
    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod);

    PaymentOrder getPaymentByOrderId(Long id);

    PaymentOrder getPaymentByOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user,Long amount,Long orderId);

    String createStripePaymentLink(UserDTO user,Long amount,Long orderId);

}

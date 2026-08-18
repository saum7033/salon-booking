package com.example.payment.service;


import com.example.payment.domain.PaymentMethod;
import com.example.payment.modal.PaymentOrder;
import com.example.payment.payload.dto.BookingDTO;
import com.example.payment.payload.dto.UserDTO;
import com.example.payment.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentByOrderId(Long id) throws Exception;

    PaymentOrder getPaymentByOrderByPaymentId(String paymentId);

    PaymentLink createRazorpayPaymentLink(UserDTO user,Long amount,Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user,Long amount,Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId,String paymentLinkId) throws RazorpayException;

}

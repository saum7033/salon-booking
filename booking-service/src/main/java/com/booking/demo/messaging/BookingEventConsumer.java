package com.booking.demo.messaging;

import com.booking.demo.modal.PaymentOrder;
import com.booking.demo.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingEventConsumer {
    private final BookingService bookingService;

    @RabbitListener(queues = "booking-queue")
    public void bookingUpdateListener(PaymentOrder paymentOrder){
        bookingService.bookingSuccess(paymentOrder);
    }
}

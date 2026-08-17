package com.booking.demo.modal;

import com.booking.demo.domain.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long salonId;
    private Long customerId;
    private LocalDateTime sartTime;
    private LocalDateTime endTime;

    @ElementCollection
    private Set<Long> serviceIds;
    private BookingStatus status = BookingStatus.PENDING;
    private int totalPrice;
}

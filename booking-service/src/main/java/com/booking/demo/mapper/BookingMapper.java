package com.booking.demo.mapper;

import com.booking.demo.dto.BookingDTO;
import com.booking.demo.modal.Booking;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setStartTime(booking.getSartTime());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setServiceIds(booking.getServiceIds());

        return bookingDTO;
    }
}

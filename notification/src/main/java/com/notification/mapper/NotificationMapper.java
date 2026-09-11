package com.notification.mapper;

import com.notification.modal.Notification;
import com.notification.payload.dto.BookingDTO;
import com.notification.payload.dto.NotificationDTO;

public class NotificationMapper {

    public static NotificationDTO toDTO(Notification notification, BookingDTO bookingDTO){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setId(notification.getId());
        notificationDTO.setType(notification.getType());
        notificationDTO.setIsRead(notification.getIsRead());
        notificationDTO.setDescription(notification.getDescription());
        notificationDTO.setBookingId(notification.getBookingId());
        notificationDTO.setUserId(notification.getUserId());
        notificationDTO.setSalonId(notification.getSalonId());
        notificationDTO.setCreatedAt(notification.getCreatedAt());
        notificationDTO.setBookingDTO(bookingDTO);

        return notificationDTO;
    }
}

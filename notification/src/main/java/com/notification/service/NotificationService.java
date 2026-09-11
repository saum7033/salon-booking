package com.notification.service;


import com.notification.modal.Notification;
import com.notification.payload.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    NotificationDTO createNotification(Notification notificationDTO);

    List<Notification> getAllNotificationByUserId(Long userId);

    List<Notification> getAllNotificationBySalonId(Long salonId);

    Notification markNotificationAsRead(Long notificationId);
}

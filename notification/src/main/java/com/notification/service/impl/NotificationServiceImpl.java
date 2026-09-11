package com.notification.service.impl;

import com.notification.mapper.NotificationMapper;
import com.notification.modal.Notification;
import com.notification.payload.dto.BookingDTO;
import com.notification.payload.dto.NotificationDTO;
import com.notification.repository.NotificationRepository;
import com.notification.service.NotificationService;
import com.notification.service.client.BookingFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final BookingFeignClient bookingFeignClient;
    @Override
    public NotificationDTO createNotification(Notification notification) {
        Notification savedNotificaton = notificationRepository.save(notification);
        BookingDTO bookingDTO = bookingFeignClient.getBookingById(savedNotificaton.getBookingId()).getBody();

        NotificationDTO notificationDTO = NotificationMapper.toDTO(savedNotificaton,bookingDTO);

        return notificationDTO;
    }

    @Override
    public List<Notification> getAllNotificationByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> getAllNotificationBySalonId(Long salonId) {
        return notificationRepository.findBySalonId(salonId);
    }

    @Override
    public Notification markNotificationAsRead(Long notificationId) {
        return notificationRepository.findById(notificationId).map(
                notification -> {
                    notification.setIsRead(true);
                    return notificationRepository.save(notification);
                }
        ).orElseThrow(()-> new Exception("Notification not found"));
    }
}

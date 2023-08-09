package danekerscode.notificationservice.service;

import danekerscode.notificationservice.utils.Notification;

public interface MailService {
    void send(Notification notification);
}

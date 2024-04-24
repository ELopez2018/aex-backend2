package com.aex.platform.service;

import com.aex.platform.entities.Role;
import com.aex.platform.entities.User;
import com.aex.platform.repository.UserRepository;
import com.aex.platform.template.EmailTemplete;
import jakarta.mail.MessagingException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class NotificationsService {
    @Autowired
    EmailService emailService;

    @Autowired
    EmailTemplete emailTemplete;

    @Autowired
    UserRepository userRepository;

    public void sendNotificationToCashiers(Integer totalTask) throws MessagingException {
        List<User> cashiers = userRepository.findAllByRole(Role.CASHIER).orElse(null);
        for (User user : cashiers) {
            emailService.sendEmail(user.getEmail(), "Nueva tarea", emailTemplete.newTask(user, totalTask));
        }
    }
    public void sendNotificationToBenficiaryTIProgress(User user) throws MessagingException {
            emailService.sendEmail(user.getEmail(), "Buenas noticias AEX", emailTemplete.toBenficiaryInProgress(user));
    }
}

package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

import com.alpacaflow.meditrack.iam.iam.domain.model.commands.CreateMockUserCommand;
import com.alpacaflow.meditrack.iam.iam.domain.services.UserCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true")
public class StaffProvisionMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaffProvisionMessageListener.class);

    private final UserCommandService userCommandService;

    public StaffProvisionMessageListener(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @JmsListener(
            destination = MessagingQueueNames.STAFF_PROVISION_REQUESTED,
            containerFactory = "jmsListenerContainerFactory")
    public StaffProvisionResponseMessage provisionStaff(StaffProvisionRequestMessage request) {
        var email = normalizeEmail(request.email());
        var role = request.role() == null ? "" : request.role().trim().toLowerCase(Locale.ROOT);
        LOGGER.info("Staff provision request via JMS for email={} role={}", email, role);

        if (email.isEmpty() || role.isBlank()) {
            throw new IllegalArgumentException("email and role are required for staff provisioning");
        }

        var existing = userCommandService.getUserByEmail(email);
        if (existing.isPresent()) {
            var user = existing.get();
            return new StaffProvisionResponseMessage(user.getId(), user.getEmail(), user.getRole());
        }

        try {
            var user = userCommandService.handle(new CreateMockUserCommand(email, role));
            return new StaffProvisionResponseMessage(user.getId(), user.getEmail(), user.getRole());
        } catch (IllegalStateException duplicate) {
            return userCommandService.getUserByEmail(email)
                    .map(user -> new StaffProvisionResponseMessage(user.getId(), user.getEmail(), user.getRole()))
                    .orElseThrow(() -> duplicate);
        }
    }

    private static String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);
    }
}

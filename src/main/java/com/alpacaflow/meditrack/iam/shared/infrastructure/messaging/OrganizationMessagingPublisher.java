package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.organization.adapter", havingValue = "jms")
public class OrganizationMessagingPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationMessagingPublisher.class);

    private final JmsTemplate jmsTemplate;

    public OrganizationMessagingPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publishAdminRegistrationRequested(AdminRegistrationRequestedMessage message) {
        LOGGER.info("Publishing admin registration request for userId={} to queue {}",
                message.userId(), MessagingQueueNames.ADMIN_REGISTRATION_REQUESTED);
        jmsTemplate.convertAndSend(MessagingQueueNames.ADMIN_REGISTRATION_REQUESTED, message);
    }
}

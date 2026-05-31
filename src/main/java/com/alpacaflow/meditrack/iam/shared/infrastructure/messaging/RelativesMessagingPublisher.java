package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.relatives.adapter", havingValue = "jms")
public class RelativesMessagingPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelativesMessagingPublisher.class);

    private final JmsTemplate jmsTemplate;

    public RelativesMessagingPublisher(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publishRelativeRegistrationRequested(RelativeRegistrationRequestedMessage message) {
        LOGGER.info("Publishing relative registration request for userId={} to queue {}",
                message.userId(), MessagingQueueNames.RELATIVE_REGISTRATION_REQUESTED);
        jmsTemplate.convertAndSend(MessagingQueueNames.RELATIVE_REGISTRATION_REQUESTED, message);
    }
}

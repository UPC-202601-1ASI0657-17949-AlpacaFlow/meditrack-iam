package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.services;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.RelativesContextFacade;
import com.alpacaflow.meditrack.iam.shared.infrastructure.messaging.RelativeRegistrationRequestedMessage;
import com.alpacaflow.meditrack.iam.shared.infrastructure.messaging.RelativesMessagingPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.relatives.adapter", havingValue = "jms")
public class RelativesContextFacadeMessagingImpl implements RelativesContextFacade {

    private final RelativesMessagingPublisher messagingPublisher;

    public RelativesContextFacadeMessagingImpl(RelativesMessagingPublisher messagingPublisher) {
        this.messagingPublisher = messagingPublisher;
    }

    @Override
    public void registerRelative(Long userId, String email, String firstName, String lastName) {
        messagingPublisher.publishRelativeRegistrationRequested(
                RelativeRegistrationRequestedMessage.of(userId, email, firstName, lastName));
    }
}

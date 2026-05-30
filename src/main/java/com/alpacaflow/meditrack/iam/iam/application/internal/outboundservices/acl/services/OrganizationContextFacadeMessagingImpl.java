package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.services;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.OrganizationContextFacade;
import com.alpacaflow.meditrack.iam.shared.infrastructure.messaging.AdminRegistrationRequestedMessage;
import com.alpacaflow.meditrack.iam.shared.infrastructure.messaging.OrganizationMessagingPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.organization.adapter", havingValue = "jms")
public class OrganizationContextFacadeMessagingImpl implements OrganizationContextFacade {

    private final OrganizationMessagingPublisher messagingPublisher;

    public OrganizationContextFacadeMessagingImpl(OrganizationMessagingPublisher messagingPublisher) {
        this.messagingPublisher = messagingPublisher;
    }

    @Override
    public void registerOrganizationWithAdmin(
            Long userId,
            String firstName,
            String lastName,
            String organizationName,
            String organizationType,
            String email) {
        messagingPublisher.publishAdminRegistrationRequested(
                AdminRegistrationRequestedMessage.of(
                        userId,
                        email,
                        firstName,
                        lastName,
                        organizationName,
                        organizationType));
    }
}

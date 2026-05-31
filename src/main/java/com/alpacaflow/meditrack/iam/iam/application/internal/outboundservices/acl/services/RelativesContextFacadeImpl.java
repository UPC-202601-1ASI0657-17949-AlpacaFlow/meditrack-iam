package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.services;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.RelativesContextFacade;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.RelativesClient;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.RemoteRelativeRegistrationRequest;
import feign.FeignException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.relatives.adapter", havingValue = "rest", matchIfMissing = true)
public class RelativesContextFacadeImpl implements RelativesContextFacade {

    private final RelativesClient relativesClient;

    public RelativesContextFacadeImpl(RelativesClient relativesClient) {
        this.relativesClient = relativesClient;
    }

    @Override
    public void registerRelative(Long userId, String email, String firstName, String lastName) {
        try {
            relativesClient.registerRelative(new RemoteRelativeRegistrationRequest(
                    userId, email, firstName, lastName));
        } catch (FeignException e) {
            throw new RuntimeException("Failed to register relative remotely: " + e.getMessage());
        }
    }
}

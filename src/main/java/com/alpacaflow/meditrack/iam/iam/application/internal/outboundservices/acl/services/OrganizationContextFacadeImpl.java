package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.services;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.OrganizationContextFacade;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.OrganizationClient;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.RemoteAdminRequest;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.RemoteOrganizationRequest;
import feign.FeignException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.organization.adapter", havingValue = "rest", matchIfMissing = true)
public class OrganizationContextFacadeImpl implements OrganizationContextFacade {

    private final OrganizationClient organizationClient;

    public OrganizationContextFacadeImpl(OrganizationClient organizationClient) {
        this.organizationClient = organizationClient;
    }

    @Override
    public void registerOrganizationWithAdmin(
            Long userId,
            String firstName,
            String lastName,
            String organizationName,
            String organizationType,
            String email) {
        var organizationRequest = new RemoteOrganizationRequest(organizationName, organizationType, email);
        try {
            var organization = organizationClient.createRemoteOrganization(organizationRequest);
            organizationClient.createRemoteAdmin(new RemoteAdminRequest(
                    organization.id(),
                    userId,
                    firstName,
                    lastName
            ));
        } catch (FeignException.Conflict e) {
            throw new RuntimeException("The organization name or email is already registered in the system.");
        } catch (FeignException e) {
            throw new RuntimeException("Failed to register organization remotely: " + e.getMessage());
        }
    }
}

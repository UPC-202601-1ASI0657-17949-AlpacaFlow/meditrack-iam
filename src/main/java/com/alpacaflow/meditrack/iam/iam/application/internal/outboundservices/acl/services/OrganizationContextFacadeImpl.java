package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.services;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.OrganizationContextFacade;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.OrganizationClient;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.RemoteOrganizationRequest;
import feign.FeignException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class OrganizationContextFacadeImpl implements OrganizationContextFacade {

    private final OrganizationClient organizationClient;

    public OrganizationContextFacadeImpl(OrganizationClient organizationClient) {
        this.organizationClient = organizationClient;
    }

    @Override
    public void createOrganization(String name, String type, String email) {
        var request = new RemoteOrganizationRequest(name, type, email);
        try {
            organizationClient.createRemoteOrganization(request);
        } catch (FeignException.Conflict e) {
            throw new RuntimeException("The organization name or email is already registered in the system.");
        } catch (FeignException e) {
            throw new RuntimeException("Failed to register organization remotely: " + e.getMessage());
        }
    }
}
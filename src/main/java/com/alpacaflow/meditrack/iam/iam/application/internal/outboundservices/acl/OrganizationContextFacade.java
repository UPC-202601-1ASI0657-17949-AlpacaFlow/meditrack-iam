package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl;

/**
 * Port (Anti-Corruption Layer) used by IAM to communicate with the Organization bounded context.
 */
public interface OrganizationContextFacade {

    /**
     * Creates the organization and its admin profile in the Organization microservice.
     */
    void registerOrganizationWithAdmin(
            Long userId,
            String firstName,
            String lastName,
            String organizationName,
            String organizationType,
            String email
    );
}

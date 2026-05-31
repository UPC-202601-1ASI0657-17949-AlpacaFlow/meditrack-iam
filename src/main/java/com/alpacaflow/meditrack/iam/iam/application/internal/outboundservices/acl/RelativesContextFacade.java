package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl;

/**
 * Port (Anti-Corruption Layer) used by IAM to communicate with the Relatives bounded context.
 */
public interface RelativesContextFacade {

    /**
     * Creates the relative profile in the Relatives microservice after a relative user signs up.
     */
    void registerRelative(Long userId, String email, String firstName, String lastName);
}

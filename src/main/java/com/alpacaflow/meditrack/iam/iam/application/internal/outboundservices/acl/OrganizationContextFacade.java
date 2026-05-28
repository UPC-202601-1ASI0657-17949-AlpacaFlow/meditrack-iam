package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl;

import java.util.Optional;

/**
 * Port (Anti-Corruption Layer) utilizado por el microservicio IAM para comunicarse con el
 * Bounded Context de Organization de forma aislada.
 */
public interface OrganizationContextFacade {

    /**
     * Envía la solicitud de creación de la organización remota.
     */
    void createOrganization(
            String name,
            String type,
            String email
    );
}
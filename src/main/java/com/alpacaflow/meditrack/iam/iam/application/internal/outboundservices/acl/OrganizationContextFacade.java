package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl;

import java.util.Optional;

/**
 * Port (Anti-Corruption Layer) utilizado por el microservicio IAM para comunicarse con el
 * Bounded Context de Organization de forma aislada.
 */
public interface OrganizationContextFacade {

    /**
     * Verifica si un nombre de organización ya está en uso o sigue disponible.
     * * @param name Nombre de la organización a verificar.
     * @return true si el nombre está disponible, false si ya existe.
     */
    boolean isOrganizationNameAvailable(String name);

    /**
     * Busca una organización externa por su identificador único.
     * * @param organizationId ID de la organización.
     * @return un Optional con la organización externa si existe.
     */
    Optional<ExternalOrganization> fetchOrganizationById(Long organizationId);

    /**
     * Notifica la creación de un nuevo administrador y su organización en el contexto correspondiente.
     * @return el ID de la organización creada de forma remota.
     */
    Long createOrganizationWithAdmin(
            String organizationName,
            String organizationType,
            Long iamUserId,
            String firstName,
            String lastName
    );
}
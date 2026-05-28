package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

/**
 * DTO (Data Transfer Object) en formato Record utilizado para estructurar el cuerpo (Body)
 * de la petición HTTP POST hacia el microservicio remoto de Organization.
 * <p>
 * Representa los datos requeridos por el otro microservicio para inicializar
 * una institución junto con su cuenta administradora de forma atómica.
 */
public record RemoteOrganizationRequest(
        String organizationName,
        String organizationType,
        Long iamUserId,
        String firstName,
        String lastName
) {
}
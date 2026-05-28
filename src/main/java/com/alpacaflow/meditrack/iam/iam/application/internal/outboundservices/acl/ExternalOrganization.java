package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl;

/**
 * Representación interna de una Organización perteneciente al Bounded Context externo de Organization.
 * Utilizado por {@link OrganizationContextFacade} para que el microservicio IAM nunca dependa
 * de los tipos de dominio de Organization directamente (Anti-Corruption Layer).
 */
public record ExternalOrganization(Long id, String name, boolean active) {
}
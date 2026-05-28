package com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.services;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.ExternalOrganization;
import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.acl.OrganizationContextFacade;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.OrganizationClient;
import com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client.RemoteOrganizationRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class OrganizationContextFacadeImpl implements OrganizationContextFacade {

    private final OrganizationClient organizationClient;

    // Spring inyecta automáticamente el cliente HTTP
    public OrganizationContextFacadeImpl(OrganizationClient organizationClient) {
        this.organizationClient = organizationClient;
    }

    @Override
    public boolean isOrganizationNameAvailable(String name) {
        if (name == null || name.isBlank()) return false;

        try {
            // LLAMADA HTTP REAL al otro microservicio
            return organizationClient.checkNameAvailability(name);
        } catch (Exception e) {
            // Plan de contingencia si el servicio remoto está caído
            System.err.println("Error de comunicación con Organization Service: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Long createOrganizationWithAdmin(String organizationName, String organizationType, Long iamUserId, String firstName, String lastName) {

        // Construimos el DTO con el formato que el otro microservicio espera por red
        var request = new RemoteOrganizationRequest(organizationName, organizationType, iamUserId, firstName, lastName);

        // PETICIÓN POST REAL a través del puente de red
        Long remoteId = organizationClient.createRemoteOrganization(request);

        System.out.println("Microservicio Organization respondió. Nuevo ID asignado: " + remoteId);
        return remoteId; // Retorna el verdadero ID de la base de datos de Organizations
    }

    @Override
    public Optional<ExternalOrganization> fetchOrganizationById(Long organizationId) {
        // Aquí harías un GET simulado o real hacia /api/v1/organizations/{id}
        return Optional.empty();
    }
}
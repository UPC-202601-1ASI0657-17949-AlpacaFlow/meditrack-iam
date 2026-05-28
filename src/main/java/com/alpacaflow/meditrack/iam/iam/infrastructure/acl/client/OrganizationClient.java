package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// "organization-service" es el nombre del microservicio o su URL configurada en Docker Compose
@FeignClient(name = "organization-service", url = "${organization.facade.url}")
public interface OrganizationClient {

    // Mapea el endpoint real del microservicio Organization que reciba los datos para crear la institución y su administrador
    @PostMapping("/api/v1/organizations")
    void createRemoteOrganization(@RequestBody RemoteOrganizationRequest request);
}
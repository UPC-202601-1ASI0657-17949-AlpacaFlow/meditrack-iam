package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.OrganizationNameAvailabilityResource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "organization-service", url = "${organization.facade.url}")
public interface OrganizationClient {

    @PostMapping("/api/v1/organizations")
    RemoteOrganizationResponse createRemoteOrganization(@RequestBody RemoteOrganizationRequest request);

    @PostMapping("/api/v1/admins")
    void createRemoteAdmin(@RequestBody RemoteAdminRequest request);

    @GetMapping("/api/v1/organizations/availability")
    OrganizationNameAvailabilityResource checkOrganizationNameAvailability(@RequestParam("name") String name);
}

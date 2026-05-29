package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "organization-service", url = "${organization.facade.url}")
public interface OrganizationClient {

    @PostMapping("/api/v1/organizations")
    RemoteOrganizationResponse createRemoteOrganization(@RequestBody RemoteOrganizationRequest request);

    @PostMapping("/api/v1/admins")
    void createRemoteAdmin(@RequestBody RemoteAdminRequest request);
}

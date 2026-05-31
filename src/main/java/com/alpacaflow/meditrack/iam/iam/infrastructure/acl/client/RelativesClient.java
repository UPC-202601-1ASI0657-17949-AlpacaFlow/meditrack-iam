package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "relatives-service", url = "${relatives.facade.url}")
public interface RelativesClient {

    @PostMapping("/api/v1/internal/relatives/registration")
    void registerRelative(@RequestBody RemoteRelativeRegistrationRequest request);
}

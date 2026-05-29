package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

public record RemoteOrganizationResponse(
        Long id,
        String name,
        String type,
        String email
) {
}

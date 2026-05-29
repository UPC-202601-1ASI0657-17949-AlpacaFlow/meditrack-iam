package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

public record RemoteAdminRequest(
        Long organizationId,
        Long userId,
        String firstName,
        String lastName
) {
}

package com.alpacaflow.meditrack.iam.iam.infrastructure.acl.client;

public record RemoteRelativeRegistrationRequest(
        Long userId,
        String email,
        String firstName,
        String lastName
) {
}

package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

import java.util.UUID;

/**
 * Async command published by IAM after an admin user signs up.
 */
public record AdminRegistrationRequestedMessage(
        String eventId,
        Long userId,
        String email,
        String firstName,
        String lastName,
        String organizationName,
        String organizationType
) {
    public static AdminRegistrationRequestedMessage of(
            Long userId,
            String email,
            String firstName,
            String lastName,
            String organizationName,
            String organizationType) {
        return new AdminRegistrationRequestedMessage(
                UUID.randomUUID().toString(),
                userId,
                email,
                firstName,
                lastName,
                organizationName,
                organizationType);
    }
}

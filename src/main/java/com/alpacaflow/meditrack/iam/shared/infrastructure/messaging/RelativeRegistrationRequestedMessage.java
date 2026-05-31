package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

import java.util.UUID;

/**
 * Async command published by IAM after a relative user signs up.
 */
public record RelativeRegistrationRequestedMessage(
        String eventId,
        Long userId,
        String email,
        String firstName,
        String lastName
) {
    public static RelativeRegistrationRequestedMessage of(
            Long userId,
            String email,
            String firstName,
            String lastName) {
        return new RelativeRegistrationRequestedMessage(
                UUID.randomUUID().toString(),
                userId,
                email,
                firstName,
                lastName);
    }
}

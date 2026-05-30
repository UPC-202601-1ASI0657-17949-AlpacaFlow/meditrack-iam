package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

/**
 * Reply with the IAM user id created (or already existing) for staff provisioning.
 */
public record StaffProvisionResponseMessage(
        Long userId,
        String email,
        String role
) {
}

package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

/**
 * Request-reply command: Organization asks IAM to provision a doctor/caregiver user.
 */
public record StaffProvisionRequestMessage(
        String email,
        String role
) {
}

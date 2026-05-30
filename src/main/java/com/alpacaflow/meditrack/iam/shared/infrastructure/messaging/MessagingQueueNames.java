package com.alpacaflow.meditrack.iam.shared.infrastructure.messaging;

/**
 * JMS destination names shared with Organization (keep in sync manually across repos).
 */
public final class MessagingQueueNames {

    public static final String ADMIN_REGISTRATION_REQUESTED = "meditrack.iam.admin-registration.requested";
    public static final String STAFF_PROVISION_REQUESTED = "meditrack.organization.staff-provision.requested";

    private MessagingQueueNames() {
    }
}

package com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String email, String role, String token) {
}


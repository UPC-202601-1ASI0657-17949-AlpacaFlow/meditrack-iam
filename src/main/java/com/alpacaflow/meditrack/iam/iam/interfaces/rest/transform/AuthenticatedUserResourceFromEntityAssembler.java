package com.alpacaflow.meditrack.iam.iam.interfaces.rest.transform;

import com.alpacaflow.meditrack.iam.iam.domain.model.aggregates.User;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getEmail(), user.getRole(), token);
    }
}


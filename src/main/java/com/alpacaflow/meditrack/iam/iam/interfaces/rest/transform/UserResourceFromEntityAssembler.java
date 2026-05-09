package com.alpacaflow.meditrack.iam.iam.interfaces.rest.transform;

import com.alpacaflow.meditrack.iam.iam.domain.model.aggregates.User;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.UserResource;

/**
 * Assembler to convert a User entity to a UserResource.
 */
public class UserResourceFromEntityAssembler {
    /**
     * Converts a User entity to a UserResource.
     *
     * @param user The {@link User} entity to convert.
     * @return The {@link UserResource} resource that results from the conversion.
     */
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}


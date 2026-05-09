package com.alpacaflow.meditrack.iam.iam.interfaces.rest.transform;

import com.alpacaflow.meditrack.iam.iam.domain.model.commands.SignUpCommand;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        return new SignUpCommand(
                resource.email(), 
                resource.password(), 
                resource.role(),
                resource.firstName(),
                resource.lastName(),
                resource.organizationName(),
                resource.organizationType()
        );
    }
}


package com.alpacaflow.meditrack.iam.iam.interfaces.rest.transform;

import com.alpacaflow.meditrack.iam.iam.domain.model.commands.SignInCommand;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        var email = signInResource.email() != null ? signInResource.email() : "";
        var password = signInResource.password() != null ? signInResource.password() : "";
        return new SignInCommand(email, password);
    }
}


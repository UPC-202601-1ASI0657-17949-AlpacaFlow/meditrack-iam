package com.alpacaflow.meditrack.iam.iam.interfaces.rest;

import com.alpacaflow.meditrack.iam.iam.domain.services.UserCommandService;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.CreateMockUserResource;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.UserResource;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.transform.CreateMockUserCommandFromResourceAssembler;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Staff user provisioning for Organization (doctor/caregiver autoprovisioning).
 * Initial password is the canonical email, matching the monolith convention.
 */
@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Staff Users", description = "IAM staff user lookup and provisioning for Organization")
public class StaffUsersController {

    private final UserCommandService userCommandService;

    public StaffUsersController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping(value = "/staff", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Provision staff user", description = "Creates a doctor/caregiver user if it does not exist.")
    public ResponseEntity<UserResource> provisionStaffUser(@RequestBody CreateMockUserResource resource) {
        try {
            var command = CreateMockUserCommandFromResourceAssembler.toCommandFromResource(resource);
            var user = userCommandService.handle(command);
            return new ResponseEntity<>(UserResourceFromEntityAssembler.toResourceFromEntity(user), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return userCommandService.getUserByEmail(resource.email())
                    .map(u -> ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(u)))
                    .orElse(ResponseEntity.badRequest().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        return userCommandService.getUserById(userId)
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserResource> getUserByEmail(@PathVariable String email) {
        return userCommandService.getUserByEmail(email)
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

package com.alpacaflow.meditrack.iam.iam.interfaces.rest;

import com.alpacaflow.meditrack.iam.iam.domain.model.aggregates.User;
import com.alpacaflow.meditrack.iam.iam.infrastructure.repositories.UserRepository;
import com.alpacaflow.meditrack.iam.iam.interfaces.rest.resources.SignUpResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        var request = new SignUpResource("test@meditrack.com", "123456");

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@meditrack.com"));

        var persistedUser = userRepository.findByNormalizedEmail("test@meditrack.com").orElseThrow();
        assertEquals("test@meditrack.com", persistedUser.getEmail());
    }

    @Test
    void shouldFailWhenEmailAlreadyExists() throws Exception {

        new User("duplicate@meditrack.com", "123456", "USER");

        var request = new SignUpResource("duplicate@meditrack.com", "123456");

        mockMvc.perform(post("/api/v1/authentication/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        var result = mockMvc.perform(post("/api/v1/authentication/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isBadRequest());

        assertTrue(result.andReturn().getResponse().getContentAsString().contains("already exists"));
    }
}
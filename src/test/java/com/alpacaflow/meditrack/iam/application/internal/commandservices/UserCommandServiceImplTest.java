package com.alpacaflow.meditrack.iam.application.internal.commandservices;

import com.alpacaflow.meditrack.iam.iam.application.internal.commandservices.UserCommandServiceImpl;
import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.hashing.HashingService;
import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.tokens.TokenService;
import com.alpacaflow.meditrack.iam.iam.domain.model.aggregates.User;
import com.alpacaflow.meditrack.iam.iam.domain.model.commands.CreateMockUserCommand;
import com.alpacaflow.meditrack.iam.iam.domain.model.commands.SignInCommand;
import com.alpacaflow.meditrack.iam.iam.domain.model.commands.SignUpCommand;
import com.alpacaflow.meditrack.iam.iam.infrastructure.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserCommandServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HashingService hashingService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserCommandServiceImpl userCommandService;

    private User user;

    @Test
    void shouldSignInSuccessfully() {

        user = new User("test@meditrack.com", "password", "USER");
        var command = new SignInCommand("test@meditrack.com", "123456");

        when(userRepository.findByNormalizedEmail("test@meditrack.com")).thenReturn(Optional.of(user));
        when(hashingService.matches("123456", "password")).thenReturn(true);
        when(tokenService.generateToken("test@meditrack.com")).thenReturn("jwt-token");

        Optional<ImmutablePair<User, String>> result = userCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals("jwt-token", result.get().getRight());
        assertEquals("test@meditrack.com", result.get().getLeft().getEmail());
        verify(userRepository).findByNormalizedEmail("test@meditrack.com");
        verify(tokenService).generateToken("test@meditrack.com");
    }

    @Test
    void shouldReturnEmptyWhenUserDoesNotExist() {

        var command = new SignInCommand("unknown@meditrack.com", "123456");
        when(userRepository.findByNormalizedEmail("unknown@meditrack.com")).thenReturn(Optional.empty());

        var result = userCommandService.handle(command);

        assertTrue(result.isEmpty());
        verify(userRepository).findByNormalizedEmail("unknown@meditrack.com");
        verifyNoInteractions(tokenService);
    }

    @Test
    void shouldReturnEmptyWhenPasswordIsInvalid() {

        user = new User("test@meditrack.com", "password", "USER");
        var command = new SignInCommand("test@meditrack.com", "wrong-password");

        when(userRepository.findByNormalizedEmail("test@meditrack.com")).thenReturn(Optional.of(user));
        when(hashingService.matches(anyString(), eq("encoded-password"))).thenReturn(false);

        var result = userCommandService.handle(command);
        assertTrue(result.isEmpty());
        verify(tokenService, never()).generateToken(anyString());
    }

    @Test
    void shouldCreateUserSuccessfully() {

        var command = new SignUpCommand("test@meditrack.com", "123456", "USER", null, null, null, null);
        when(userRepository.findByNormalizedEmail("test@meditrack.com")).thenReturn(Optional.empty());
        when(hashingService.encode("123456")).thenReturn("password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        var result = userCommandService.handle(command);

        assertTrue(result.isPresent());
        assertEquals("test@meditrack.com", result.get().getEmail());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();
        assertEquals("password", savedUser.getPassword());
    }
}
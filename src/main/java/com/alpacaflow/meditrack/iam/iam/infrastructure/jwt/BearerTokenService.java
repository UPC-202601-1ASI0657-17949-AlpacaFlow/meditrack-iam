package com.alpacaflow.meditrack.iam.iam.infrastructure.jwt;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest token);
    String generateToken(Authentication authentication);
}


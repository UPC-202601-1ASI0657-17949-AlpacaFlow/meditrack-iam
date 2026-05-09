package com.alpacaflow.meditrack.iam.iam.infrastructure.bcrypt;

import com.alpacaflow.meditrack.iam.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}


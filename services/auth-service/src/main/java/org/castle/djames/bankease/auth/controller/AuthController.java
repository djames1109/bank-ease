package org.castle.djames.bankease.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.castle.djames.bankease.auth.service.JwtService;
import org.castle.djames.bankease.persistence.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @GetMapping("/auth/token/{username}")
    public String generateToken(@PathVariable String username) {
        log.info("Generating token for user: {}", username);
        var user = userRepository.findByUsername(username).orElseThrow();
        return jwtService.generateToken(user);

    }
}

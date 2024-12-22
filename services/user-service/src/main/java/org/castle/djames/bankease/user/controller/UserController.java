package org.castle.djames.bankease.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/v1/users")
@RestController
public class UserController {

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        log.info("Test");
        return ResponseEntity.ok().build();
    }
}

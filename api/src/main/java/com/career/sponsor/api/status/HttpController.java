package com.career.sponsor.api.status;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpController {

    @GetMapping("/api/v1/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}

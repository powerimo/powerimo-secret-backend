package org.powerimo.secret.server.controllers;

import lombok.RequiredArgsConstructor;
import org.powerimo.secret.api.models.ActionCompleted;
import org.powerimo.secret.server.services.SecretManagerService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("service")
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.service-endpoint-enabled", havingValue = "true", matchIfMissing = false)
public class ServiceController {
    private final SecretManagerService secretManagerService;
    private final static String CLEANUP_SOURCE = "ServiceController";

    @PostMapping("cleanup")
    public ResponseEntity<ActionCompleted> postCleanup() {
        if (secretManagerService.isCleanupIsRunning()) {
            return ResponseEntity.ok(ActionCompleted.builder()
                            .action("CLEANUP")
                            .message("Already running")
                            .status("RUNNING")
                    .build());
        }

        CompletableFuture.runAsync(() -> {
            secretManagerService.cleanup(CLEANUP_SOURCE);
        });

        return ResponseEntity.ok(ActionCompleted.builder()
                        .action("CLEANUP")
                        .status("STARTED")
                .build());
    }
}

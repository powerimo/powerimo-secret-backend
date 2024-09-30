package org.powerimo.secret.api;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecretInfo {
    private String code;
    private String url;
    private Instant createdAt;
    private Instant expiresAt;
    private long hitCount;
}

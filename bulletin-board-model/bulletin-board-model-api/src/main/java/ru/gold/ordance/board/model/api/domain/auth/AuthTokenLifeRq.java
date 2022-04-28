package ru.gold.ordance.board.model.api.domain.auth;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.AuthRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthTokenLifeRq implements AuthRq {
    private final String token;
}

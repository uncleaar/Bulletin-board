package ru.gold.ordance.board.web.api.auth;

import lombok.*;
import ru.gold.ordance.board.web.api.AuthRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthTokenLifeRq implements AuthRq {
    private static final long serialVersionUID = 1L;

    private final String token;
}

package ru.gold.ordance.board.model.api.domain.auth;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.AuthRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthLoginRq implements AuthRq {
    private final String login;

    private final String password;
}

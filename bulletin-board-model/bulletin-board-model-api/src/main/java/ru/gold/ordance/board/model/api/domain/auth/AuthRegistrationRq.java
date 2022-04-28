package ru.gold.ordance.board.model.api.domain.auth;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.AuthRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class AuthRegistrationRq implements AuthRq {
    private final String login;

    private final String password;

    private final String name;

    private final String phoneNumber;
}

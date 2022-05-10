package ru.gold.ordance.board.model.api.domain.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@Getter
@ToString
public class AuthLoginRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final String role;

    private final String token;

    public static AuthLoginRs success(String token, String role) {
        return AuthLoginRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .role(role)
                .token(token)
                .build();
    }

    public static AuthLoginRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AuthLoginRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

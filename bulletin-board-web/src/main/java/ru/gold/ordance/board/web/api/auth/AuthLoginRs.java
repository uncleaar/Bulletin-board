package ru.gold.ordance.board.web.api.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;
import ru.gold.ordance.board.web.api.client.WebClient;

@Builder
@Getter
@ToString
public class AuthLoginRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final WebClient webClient;

    private final String token;

    public static AuthLoginRs success(WebClient webClient, String token) {
        return AuthLoginRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .webClient(webClient)
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

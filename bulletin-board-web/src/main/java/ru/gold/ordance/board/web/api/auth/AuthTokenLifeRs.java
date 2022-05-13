package ru.gold.ordance.board.web.api.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

@Builder
@Getter
@ToString
public class AuthTokenLifeRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final boolean isValid;

    public static AuthTokenLifeRs success(boolean isValid) {
        return AuthTokenLifeRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isValid(isValid)
                .build();
    }

    public static AuthTokenLifeRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AuthTokenLifeRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

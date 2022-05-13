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
public class AuthRegistrationRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static AuthRegistrationRs success() {
        return AuthRegistrationRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static AuthRegistrationRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AuthRegistrationRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

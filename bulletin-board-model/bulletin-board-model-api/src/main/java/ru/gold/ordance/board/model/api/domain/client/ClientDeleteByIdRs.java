package ru.gold.ordance.board.model.api.domain.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@Getter
@ToString
public class ClientDeleteByIdRs implements Rs {
    private final Status status;

    public static ClientDeleteByIdRs success() {
        return ClientDeleteByIdRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static ClientDeleteByIdRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return ClientDeleteByIdRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

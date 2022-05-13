package ru.gold.ordance.board.web.api.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

@Builder
@Getter
@ToString
public class ClientUpdateRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final boolean isUpdated;

    private final Long entityId;

    public static ClientUpdateRs success(boolean isUpdated, Long entityId) {
        return ClientUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isUpdated(isUpdated)
                .entityId(entityId)
                .build();
    }

    public static ClientUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return ClientUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

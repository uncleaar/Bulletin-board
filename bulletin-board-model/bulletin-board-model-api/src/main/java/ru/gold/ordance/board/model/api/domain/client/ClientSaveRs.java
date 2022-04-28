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
public class ClientSaveRs implements Rs {
    private final Status status;

    private final Long entityId;

    public static ClientSaveRs success(Long entityId) {
        return ClientSaveRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .entityId(entityId)
                .build();
    }

    public static ClientSaveRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return ClientSaveRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}


package ru.gold.ordance.board.model.api.domain.street;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@Getter
@ToString
public class StreetUpdateRs implements Rs {
    private final Status status;

    private final boolean isUpdated;

    private final Long entityId;

    public static StreetUpdateRs success(boolean isUpdated, Long entityId) {
        return StreetUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isUpdated(isUpdated)
                .entityId(entityId)
                .build();
    }

    public static StreetUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return StreetUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

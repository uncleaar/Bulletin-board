package ru.gold.ordance.board.model.api.domain.lnk;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@Getter
@ToString
public class LnkLocalityStreetUpdateRs implements Rs {
    private final Status status;

    private final boolean isUpdated;

    private final Long entityId;

    public static LnkLocalityStreetUpdateRs success(boolean isUpdated, Long entityId) {
        return LnkLocalityStreetUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isUpdated(isUpdated)
                .entityId(entityId)
                .build();
    }

    public static LnkLocalityStreetUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LnkLocalityStreetUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

package ru.gold.ordance.board.model.api.domain.advertisement;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@Getter
@ToString
public class AdvertisementUpdateRs implements Rs {
    private final Status status;

    private final boolean isUpdated;

    private final Long entityId;

    public static AdvertisementUpdateRs success(boolean isUpdated, Long entityId) {
        return AdvertisementUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isUpdated(isUpdated)
                .entityId(entityId)
                .build();
    }

    public static AdvertisementUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AdvertisementUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

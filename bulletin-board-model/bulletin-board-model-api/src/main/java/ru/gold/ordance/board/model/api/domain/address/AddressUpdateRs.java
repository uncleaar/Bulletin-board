package ru.gold.ordance.board.model.api.domain.address;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@Getter
@ToString
public class AddressUpdateRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final boolean isUpdated;

    private final Long entityId;

    public static AddressUpdateRs success(boolean isUpdated, Long entityId) {
        return AddressUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isUpdated(isUpdated)
                .entityId(entityId)
                .build();
    }

    public static AddressUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AddressUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

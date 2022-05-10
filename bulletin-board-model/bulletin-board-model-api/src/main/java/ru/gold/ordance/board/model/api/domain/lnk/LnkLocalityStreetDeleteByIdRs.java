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
public class LnkLocalityStreetDeleteByIdRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static LnkLocalityStreetDeleteByIdRs success() {
        return LnkLocalityStreetDeleteByIdRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static LnkLocalityStreetDeleteByIdRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LnkLocalityStreetDeleteByIdRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

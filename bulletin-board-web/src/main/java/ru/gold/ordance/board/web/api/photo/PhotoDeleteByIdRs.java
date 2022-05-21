package ru.gold.ordance.board.web.api.photo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;
import ru.gold.ordance.board.web.api.region.RegionDeleteByIdRs;

@Builder
@Getter
@ToString
public class PhotoDeleteByIdRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    public static PhotoDeleteByIdRs success() {
        return PhotoDeleteByIdRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .build();
    }

    public static PhotoDeleteByIdRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return PhotoDeleteByIdRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

package ru.gold.ordance.board.web.api.photo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

@Builder
@Getter
@ToString
public class PhotoSaveRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final Long entityId;

    private final String urn;

    public static PhotoSaveRs success(Long entityId, String urn) {
        return PhotoSaveRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .entityId(entityId)
                .urn(urn)
                .build();
    }

    public static PhotoSaveRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return PhotoSaveRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

package ru.gold.ordance.board.web.api.subcategory;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

@Builder
@Getter
@ToString
public class SubcategoryUpdateRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final boolean isUpdated;

    private final Long entityId;

    public static SubcategoryUpdateRs success(boolean isUpdated, Long entityId) {
        return SubcategoryUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .isUpdated(isUpdated)
                .entityId(entityId)
                .build();
    }

    public static SubcategoryUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return SubcategoryUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

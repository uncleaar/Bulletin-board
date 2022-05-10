package ru.gold.ordance.board.model.api.domain.lnk;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class LnkLocalityStreetGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebLnkLocalityStreet> lnkLocalityStreets;

    private final Integer total;

    public static LnkLocalityStreetGetRs success(List<WebLnkLocalityStreet> lnkLocalityStreets) {
        return LnkLocalityStreetGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .lnkLocalityStreets(lnkLocalityStreets)
                .total(lnkLocalityStreets.size())
                .build();
    }

    public static LnkLocalityStreetGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LnkLocalityStreetGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

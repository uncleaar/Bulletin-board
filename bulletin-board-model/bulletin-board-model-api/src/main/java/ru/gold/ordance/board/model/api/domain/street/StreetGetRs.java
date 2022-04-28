package ru.gold.ordance.board.model.api.domain.street;

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
public class StreetGetRs implements Rs {
    private final Status status;

    private final List<WebStreet> streetList;

    private final Integer total;

    public static StreetGetRs success(List<WebStreet> streetList) {
        return StreetGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .streetList(streetList)
                .total(streetList.size())
                .build();
    }

    public static StreetGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return StreetGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

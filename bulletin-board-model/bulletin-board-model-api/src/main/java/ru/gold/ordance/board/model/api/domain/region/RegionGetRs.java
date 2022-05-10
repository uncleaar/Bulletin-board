package ru.gold.ordance.board.model.api.domain.region;

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
public class RegionGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebRegion> regionList;

    private final Integer total;

    public static RegionGetRs success(List<WebRegion> regionList) {
        return RegionGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .regionList(regionList)
                .total(regionList.size())
                .build();
    }

    public static RegionGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return RegionGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

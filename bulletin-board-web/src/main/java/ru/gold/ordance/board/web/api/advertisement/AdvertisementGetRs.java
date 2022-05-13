package ru.gold.ordance.board.web.api.advertisement;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class AdvertisementGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebAdvertisement> advertisementList;

    private final Integer total;

    public static AdvertisementGetRs success(List<WebAdvertisement> advertisementList) {
        return AdvertisementGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .advertisementList(advertisementList)
                .total(advertisementList.size())
                .build();
    }

    public static AdvertisementGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AdvertisementGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

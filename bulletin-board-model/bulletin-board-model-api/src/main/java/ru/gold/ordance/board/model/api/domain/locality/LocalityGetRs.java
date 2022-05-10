package ru.gold.ordance.board.model.api.domain.locality;

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
public class LocalityGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebLocality> localityList;

    private final Integer total;

    public static LocalityGetRs success(List<WebLocality> localityList) {
        return LocalityGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .localityList(localityList)
                .total(localityList.size())
                .build();
    }

    public static LocalityGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return LocalityGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

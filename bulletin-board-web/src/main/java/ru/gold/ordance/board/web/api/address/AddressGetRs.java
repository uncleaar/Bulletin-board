package ru.gold.ordance.board.web.api.address;

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
public class AddressGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebAddress> addressList;

    private final Integer total;

    public static AddressGetRs success(List<WebAddress> addressList) {
        return AddressGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .addressList(addressList)
                .total(addressList.size())
                .build();
    }

    public static AddressGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return AddressGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

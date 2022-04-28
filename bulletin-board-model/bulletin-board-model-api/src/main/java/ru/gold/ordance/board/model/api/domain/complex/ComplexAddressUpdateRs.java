package ru.gold.ordance.board.model.api.domain.complex;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ComplexAddressUpdateRs implements Rs {
    private final Status status;

    private final Long regionId;

    private final Long localityId;

    private final Long streetId;

    public static ComplexAddressUpdateRs success(Long regionId, Long localityId, Long streetId) {
        return ComplexAddressUpdateRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .regionId(regionId)
                .localityId(localityId)
                .streetId(streetId)
                .build();
    }

    public static ComplexAddressUpdateRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return ComplexAddressUpdateRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

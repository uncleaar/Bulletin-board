package ru.gold.ordance.board.web.service.complex;

import ru.gold.ordance.board.model.api.domain.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.model.api.domain.complex.ComplexAddressUpdateRs;

public interface ComplexAddressWebService {
    ComplexAddressUpdateRs update(ComplexAddressUpdateRq rq);
}

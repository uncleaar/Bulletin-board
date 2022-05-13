package ru.gold.ordance.board.web.service.complex;

import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRs;

public interface ComplexAddressWebService {
    ComplexAddressUpdateRs update(ComplexAddressUpdateRq rq);
}

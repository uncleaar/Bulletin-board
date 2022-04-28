package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.model.api.domain.lnk.*;

public interface LnkLocalityStreetWebService {
    LnkLocalityStreetGetRs findAll();
    LnkLocalityStreetGetRs findById(LnkLocalityStreetGetByIdRq rq);
    LnkLocalityStreetGetRs findByLocalityAndStreet(LnkLocalityStreetGetByLSRq rq);
    LnkLocalityStreetGetRs findByLocality(LnkLocalityStreetGetByLocalityRq rq);
    LnkLocalityStreetGetRs findByStreet(LnkLocalityStreetGetByStreetRq rq);
    LnkLocalityStreetUpdateRs update(LnkLocalityStreetUpdateRq rq);
    LnkLocalityStreetDeleteByIdRs deleteById(LnkLocalityStreetDeleteByIdRq rq);
}

package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.web.api.street.*;

public interface StreetWebService {
    StreetGetRs findAll();
    StreetGetRs findById(StreetGetByIdRq rq);
    StreetGetRs findByName(StreetGetByNameRq rq);
    StreetUpdateRs update(StreetUpdateRq rq);
    StreetDeleteByIdRs deleteById(StreetDeleteByIdRq rq);
}

package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.model.api.domain.region.*;

public interface RegionWebService {
    RegionGetRs findAll();
    RegionGetRs findById(RegionGetByIdRq rq);
    RegionGetRs findByName(RegionGetByNameRq rq);
    RegionUpdateRs update(RegionUpdateRq rq);
    RegionDeleteByIdRs deleteById(RegionDeleteByIdRq rq);
}

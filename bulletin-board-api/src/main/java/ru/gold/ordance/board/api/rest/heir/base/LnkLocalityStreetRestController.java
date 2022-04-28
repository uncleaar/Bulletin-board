package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.lnk.*;

public interface LnkLocalityStreetRestController extends AbstractBaseSearchRestController<LnkLocalityStreetGetRs>, AbstractUpdateRestController<LnkLocalityStreetUpdateRq, LnkLocalityStreetUpdateRs>, AbstractDeleteRestController<LnkLocalityStreetDeleteByIdRs> {
    LnkLocalityStreetGetRs findByLocality(Long localityId);
    LnkLocalityStreetGetRs findByStreet(Long streetId);
    LnkLocalityStreetGetRs findByLocalityAndStreet(Long localityId, Long streetId);
}

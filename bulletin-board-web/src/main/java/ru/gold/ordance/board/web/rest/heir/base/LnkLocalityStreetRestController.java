package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.lnk.*;

public interface LnkLocalityStreetRestController extends AbstractBaseSearchRestController<LnkLocalityStreetGetRs>, AbstractUpdateRestController<LnkLocalityStreetUpdateRq, LnkLocalityStreetUpdateRs>, AbstractDeleteRestController<LnkLocalityStreetDeleteByIdRs> {
    LnkLocalityStreetGetRs findByLocality(Long localityId);
    LnkLocalityStreetGetRs findByStreet(Long streetId);
    LnkLocalityStreetGetRs findByLocalityAndStreet(Long localityId, Long streetId);
}

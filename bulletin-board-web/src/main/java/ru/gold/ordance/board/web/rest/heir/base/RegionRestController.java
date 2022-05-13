package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.region.*;

public interface RegionRestController extends AbstractBaseSearchRestController<RegionGetRs>, AbstractUpdateRestController<RegionUpdateRq, RegionUpdateRs>, AbstractDeleteRestController<RegionDeleteByIdRs> {
    RegionGetRs findByName(String name);
}

package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.region.*;

public interface RegionRestController extends AbstractBaseSearchRestController<RegionGetRs>, AbstractUpdateRestController<RegionUpdateRq, RegionUpdateRs>, AbstractDeleteRestController<RegionDeleteByIdRs> {
    RegionGetRs findByName(String name);
}

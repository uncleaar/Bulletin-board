package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.street.*;

public interface StreetRestController extends AbstractBaseSearchRestController<StreetGetRs>, AbstractUpdateRestController<StreetUpdateRq, StreetUpdateRs>, AbstractDeleteRestController<StreetDeleteByIdRs> {
    StreetGetRs findByName(String name);
}

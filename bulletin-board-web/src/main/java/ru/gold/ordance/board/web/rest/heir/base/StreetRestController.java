package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.street.*;

public interface StreetRestController extends AbstractBaseSearchRestController<StreetGetRs>, AbstractUpdateRestController<StreetUpdateRq, StreetUpdateRs>, AbstractDeleteRestController<StreetDeleteByIdRs> {
    StreetGetRs findByName(String name);
}

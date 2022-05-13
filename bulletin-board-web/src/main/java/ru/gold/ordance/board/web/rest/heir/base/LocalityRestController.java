package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.locality.LocalityDeleteByIdRs;
import ru.gold.ordance.board.web.api.locality.LocalityGetRs;
import ru.gold.ordance.board.web.api.locality.LocalityUpdateRq;
import ru.gold.ordance.board.web.api.locality.LocalityUpdateRs;

public interface LocalityRestController extends AbstractBaseSearchRestController<LocalityGetRs>, AbstractUpdateRestController<LocalityUpdateRq, LocalityUpdateRs>, AbstractDeleteRestController<LocalityDeleteByIdRs> {
    LocalityGetRs findByName(String name);
}

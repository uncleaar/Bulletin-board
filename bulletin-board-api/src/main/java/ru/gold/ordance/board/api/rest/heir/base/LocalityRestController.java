package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.locality.LocalityDeleteByIdRs;
import ru.gold.ordance.board.model.api.domain.locality.LocalityGetRs;
import ru.gold.ordance.board.model.api.domain.locality.LocalityUpdateRq;
import ru.gold.ordance.board.model.api.domain.locality.LocalityUpdateRs;

public interface LocalityRestController extends AbstractBaseSearchRestController<LocalityGetRs>, AbstractUpdateRestController<LocalityUpdateRq, LocalityUpdateRs>, AbstractDeleteRestController<LocalityDeleteByIdRs> {
    LocalityGetRs findByName(String name);
}

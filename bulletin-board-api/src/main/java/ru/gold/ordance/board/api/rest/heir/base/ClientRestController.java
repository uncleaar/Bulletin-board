package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.client.*;

public interface ClientRestController extends AbstractBaseSearchRestController<ClientGetRs>, AbstractUpdateRestController<ClientUpdateRq, ClientUpdateRs>, AbstractDeleteRestController<ClientDeleteByIdRs> {
    ClientGetRs findByName(String name);
    ClientGetRs findByLogin(String login);
    ClientSaveRs save(ClientSaveRq rq);
}

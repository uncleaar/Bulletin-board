package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.client.*;

public interface ClientRestController extends AbstractBaseSearchRestController<ClientGetRs>, AbstractUpdateRestController<ClientUpdateRq, ClientUpdateRs>, AbstractDeleteRestController<ClientDeleteByIdRs> {
    ClientGetRs findByName(String name);
    ClientGetRs findByLogin(String login);
    ClientSaveRs save(ClientSaveRq rq);
}

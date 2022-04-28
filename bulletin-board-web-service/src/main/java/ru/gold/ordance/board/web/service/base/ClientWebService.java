package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.model.api.domain.client.*;

public interface ClientWebService {
    ClientGetRs findAll();
    ClientGetRs findById(ClientGetByIdRq rq);
    ClientGetRs findByName(ClientGetByNameRq rq);
    ClientGetRs findByLogin(ClientGetByLoginRq rq);
    ClientSaveRs save(ClientSaveRq rq);
    ClientUpdateRs update(ClientUpdateRq rq);
    ClientDeleteByIdRs deleteById(ClientDeleteByIdRq rq);
}

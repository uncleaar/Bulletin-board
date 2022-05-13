package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.client.ClientSaveRq;
import ru.gold.ordance.board.web.api.client.ClientUpdateRq;
import ru.gold.ordance.board.web.api.client.WebClient;
import ru.gold.ordance.board.core.entity.Client;

public interface ClientMapper {
    Client fromRequest(ClientUpdateRq rq);

    Client fromRequest(ClientSaveRq rq);

    WebClient fromEntity(Client entity);
}

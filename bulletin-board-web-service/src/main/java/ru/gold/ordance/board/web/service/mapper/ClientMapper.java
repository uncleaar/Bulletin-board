package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.client.ClientSaveRq;
import ru.gold.ordance.board.model.api.domain.client.ClientUpdateRq;
import ru.gold.ordance.board.model.api.domain.client.WebClient;
import ru.gold.ordance.board.model.entity.domain.Client;

public interface ClientMapper {
    Client fromRequest(ClientUpdateRq rq);

    Client fromRequest(ClientSaveRq rq);

    WebClient fromEntity(Client entity);
}

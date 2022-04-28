package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.model.api.domain.client.ClientSaveRq;
import ru.gold.ordance.board.model.api.domain.client.ClientUpdateRq;
import ru.gold.ordance.board.model.api.domain.client.WebClient;
import ru.gold.ordance.board.model.entity.domain.Client;
import ru.gold.ordance.board.web.service.mapper.ClientMapper;

public class ClientMapperImpl implements ClientMapper {
    @Override
    public Client fromRequest(ClientUpdateRq rq) {
        return Client.builder()
                .id(rq.getEntityId())
                .password(rq.getPassword())
                .name(rq.getName())
                .phoneNumber(rq.getPhoneNumber())
                .build();
    }

    @Override
    public Client fromRequest(ClientSaveRq rq) {
        return Client.builder()
                .login(rq.getLogin())
                .password(rq.getPassword())
                .name(rq.getName())
                .phoneNumber(rq.getPhoneNumber())
                .build();
    }

    @Override
    public WebClient fromEntity(Client entity) {
        return WebClient.builder()
                .entityId(entity.getId())
                .login(entity.getLogin())
                .name(entity.getName())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}

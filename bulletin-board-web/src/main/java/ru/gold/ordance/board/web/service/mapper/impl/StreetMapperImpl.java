package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.web.api.street.StreetUpdateRq;
import ru.gold.ordance.board.web.api.street.WebStreet;
import ru.gold.ordance.board.core.entity.Street;
import ru.gold.ordance.board.web.service.mapper.StreetMapper;

public class StreetMapperImpl implements StreetMapper {
    @Override
    public Street fromRequest(StreetUpdateRq rq) {
        return Street.builder()
                .id(rq.getEntityId())
                .name(rq.getName())
                .build();
    }

    @Override
    public WebStreet fromEntity(Street entity) {
        return WebStreet.builder()
                .entityId(entity.getId())
                .name(entity.getName())
                .build();
    }
}

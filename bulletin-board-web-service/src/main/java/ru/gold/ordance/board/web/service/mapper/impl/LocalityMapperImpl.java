package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.model.api.domain.locality.LocalityUpdateRq;
import ru.gold.ordance.board.model.api.domain.locality.WebLocality;
import ru.gold.ordance.board.model.entity.domain.Locality;
import ru.gold.ordance.board.model.entity.domain.Region;
import ru.gold.ordance.board.web.service.mapper.LocalityMapper;

public class LocalityMapperImpl implements LocalityMapper {
    @Override
    public Locality fromRequest(LocalityUpdateRq rq) {
        return Locality.builder()
                .id(rq.getEntityId())
                .name(rq.getName())
                .region(Region.builder()
                        .id(rq.getRegionId())
                        .build())
                .build();
    }

    @Override
    public WebLocality fromEntity(Locality entity) {
        return WebLocality.builder()
                .entityId(entity.getId())
                .name(entity.getName())
                .regionId(entity.getRegion().getId())
                .build();
    }
}

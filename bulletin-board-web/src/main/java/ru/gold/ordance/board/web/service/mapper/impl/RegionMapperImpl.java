package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.web.api.region.RegionUpdateRq;
import ru.gold.ordance.board.web.api.region.WebRegion;
import ru.gold.ordance.board.core.entity.Region;
import ru.gold.ordance.board.web.service.mapper.RegionMapper;

public class RegionMapperImpl implements RegionMapper {
    @Override
    public Region fromRequest(RegionUpdateRq rq) {
        return Region.builder()
                .id(rq.getEntityId())
                .name(rq.getName())
                .build();
    }

    @Override
    public WebRegion fromEntity(Region entity) {
        return WebRegion.builder()
                .entityId(entity.getId())
                .name(entity.getName())
                .build();
    }
}

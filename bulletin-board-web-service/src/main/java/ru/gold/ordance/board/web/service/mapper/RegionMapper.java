package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.region.RegionUpdateRq;
import ru.gold.ordance.board.model.api.domain.region.WebRegion;
import ru.gold.ordance.board.model.entity.domain.Region;

public interface RegionMapper {
    Region fromRequest(RegionUpdateRq rq);

    WebRegion fromEntity(Region entity);
}

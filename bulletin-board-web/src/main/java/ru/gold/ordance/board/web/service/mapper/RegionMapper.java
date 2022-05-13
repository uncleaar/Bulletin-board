package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.region.RegionUpdateRq;
import ru.gold.ordance.board.web.api.region.WebRegion;
import ru.gold.ordance.board.core.entity.Region;

public interface RegionMapper {
    Region fromRequest(RegionUpdateRq rq);

    WebRegion fromEntity(Region entity);
}

package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.lnk.LnkLocalityStreetUpdateRq;
import ru.gold.ordance.board.web.api.lnk.WebLnkLocalityStreet;
import ru.gold.ordance.board.core.entity.LnkLocalityStreet;

public interface LnkLocalityStreetMapper {
    LnkLocalityStreet fromRequest(LnkLocalityStreetUpdateRq rq);

    WebLnkLocalityStreet fromEntity(LnkLocalityStreet entity);
}

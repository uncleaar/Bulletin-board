package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.lnk.LnkLocalityStreetUpdateRq;
import ru.gold.ordance.board.model.api.domain.lnk.WebLnkLocalityStreet;
import ru.gold.ordance.board.model.entity.domain.LnkLocalityStreet;

public interface LnkLocalityStreetMapper {
    LnkLocalityStreet fromRequest(LnkLocalityStreetUpdateRq rq);

    WebLnkLocalityStreet fromEntity(LnkLocalityStreet entity);
}

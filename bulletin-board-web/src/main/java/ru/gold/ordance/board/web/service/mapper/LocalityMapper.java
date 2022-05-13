package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.locality.LocalityUpdateRq;
import ru.gold.ordance.board.web.api.locality.WebLocality;
import ru.gold.ordance.board.core.entity.Locality;

public interface LocalityMapper {
    Locality fromRequest(LocalityUpdateRq rq);

    WebLocality fromEntity(Locality entity);
}

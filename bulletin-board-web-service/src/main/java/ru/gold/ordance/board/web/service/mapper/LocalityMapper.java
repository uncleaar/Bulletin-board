package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.locality.LocalityUpdateRq;
import ru.gold.ordance.board.model.api.domain.locality.WebLocality;
import ru.gold.ordance.board.model.entity.domain.Locality;

public interface LocalityMapper {
    Locality fromRequest(LocalityUpdateRq rq);

    WebLocality fromEntity(Locality entity);
}

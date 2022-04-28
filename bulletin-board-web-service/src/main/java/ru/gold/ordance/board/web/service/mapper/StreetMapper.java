package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.street.StreetUpdateRq;
import ru.gold.ordance.board.model.api.domain.street.WebStreet;
import ru.gold.ordance.board.model.entity.domain.Street;

public interface StreetMapper {
    Street fromRequest(StreetUpdateRq rq);

    WebStreet fromEntity(Street entity);
}

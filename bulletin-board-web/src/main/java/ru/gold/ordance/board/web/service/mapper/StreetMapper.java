package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.street.StreetUpdateRq;
import ru.gold.ordance.board.web.api.street.WebStreet;
import ru.gold.ordance.board.core.entity.Street;

public interface StreetMapper {
    Street fromRequest(StreetUpdateRq rq);

    WebStreet fromEntity(Street entity);
}

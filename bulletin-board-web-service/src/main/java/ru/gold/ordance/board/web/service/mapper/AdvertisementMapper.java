package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.model.api.domain.advertisement.WebAdvertisement;
import ru.gold.ordance.board.model.entity.domain.Advertisement;

public interface AdvertisementMapper {
    Advertisement fromRequest(AdvertisementUpdateRq rq);

    WebAdvertisement fromEntity(Advertisement entity);
}

package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.web.api.advertisement.WebAdvertisement;
import ru.gold.ordance.board.core.entity.Advertisement;

public interface AdvertisementMapper {
    Advertisement fromRequest(AdvertisementUpdateRq rq);

    WebAdvertisement fromEntity(Advertisement entity);
}

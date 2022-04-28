package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.model.api.domain.advertisement.*;

public interface AdvertisementWebService {
    AdvertisementGetRs findAll();
    AdvertisementGetRs findById(AdvertisementGetByIdRq rq);
    AdvertisementGetRs findByName(AdvertisementGetByNameRq rq);
    AdvertisementUpdateRs update(AdvertisementUpdateRq rq);
    AdvertisementDeleteByIdRs deleteById(AdvertisementDeleteByIdRq rq);
}

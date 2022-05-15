package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.web.api.advertisement.*;

public interface AdvertisementWebService {
    AdvertisementGetRs findAll();
    AdvertisementGetRs findById(AdvertisementGetByIdRq rq);
    AdvertisementGetRs findByCategoryName(AdvertisementGetByCategoryNameRq rq);
    AdvertisementGetRs findByRegionName(AdvertisementGetByRegionNameRq rq);
    AdvertisementGetRs findByCategoryNameAndRegionName(AdvertisementGetByCategoryNameAndRegionNameRq rq);
    AdvertisementGetRs findByName(AdvertisementGetByNameRq rq);
    AdvertisementUpdateRs update(AdvertisementUpdateRq rq);
    AdvertisementDeleteByIdRs deleteById(AdvertisementDeleteByIdRq rq);
}

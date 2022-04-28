package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.advertisement.AdvertisementDeleteByIdRs;
import ru.gold.ordance.board.model.api.domain.advertisement.AdvertisementGetRs;
import ru.gold.ordance.board.model.api.domain.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.model.api.domain.advertisement.AdvertisementUpdateRs;

public interface AdvertisementRestController extends AbstractBaseSearchRestController<AdvertisementGetRs>, AbstractUpdateRestController<AdvertisementUpdateRq, AdvertisementUpdateRs>, AbstractDeleteRestController<AdvertisementDeleteByIdRs> {
    AdvertisementGetRs findByName(String name);
}

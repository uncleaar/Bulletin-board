package ru.gold.ordance.board.api.rest.heir.base;

import ru.gold.ordance.board.api.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.api.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.api.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.model.api.domain.address.AddressDeleteByIdRs;
import ru.gold.ordance.board.model.api.domain.address.AddressGetRs;
import ru.gold.ordance.board.model.api.domain.address.AddressUpdateRq;
import ru.gold.ordance.board.model.api.domain.address.AddressUpdateRs;

public interface AddressRestController extends AbstractBaseSearchRestController<AddressGetRs>, AbstractUpdateRestController<AddressUpdateRq, AddressUpdateRs>, AbstractDeleteRestController<AddressDeleteByIdRs> {
}

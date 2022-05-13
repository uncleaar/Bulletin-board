package ru.gold.ordance.board.web.rest.heir.base;

import ru.gold.ordance.board.web.rest.AbstractBaseSearchRestController;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;
import ru.gold.ordance.board.web.rest.AbstractUpdateRestController;
import ru.gold.ordance.board.web.api.address.AddressDeleteByIdRs;
import ru.gold.ordance.board.web.api.address.AddressGetRs;
import ru.gold.ordance.board.web.api.address.AddressUpdateRq;
import ru.gold.ordance.board.web.api.address.AddressUpdateRs;

public interface AddressRestController extends AbstractBaseSearchRestController<AddressGetRs>, AbstractUpdateRestController<AddressUpdateRq, AddressUpdateRs>, AbstractDeleteRestController<AddressDeleteByIdRs> {
}

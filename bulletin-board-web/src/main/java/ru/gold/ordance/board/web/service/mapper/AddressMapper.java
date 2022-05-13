package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.web.api.address.AddressUpdateRq;
import ru.gold.ordance.board.web.api.address.WebAddress;
import ru.gold.ordance.board.core.entity.Address;

public interface AddressMapper {
    Address fromRequest(AddressUpdateRq rq);

    WebAddress fromEntity(Address entity);
}

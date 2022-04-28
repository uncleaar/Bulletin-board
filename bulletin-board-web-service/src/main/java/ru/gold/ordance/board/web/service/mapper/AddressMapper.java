package ru.gold.ordance.board.web.service.mapper;

import ru.gold.ordance.board.model.api.domain.address.AddressUpdateRq;
import ru.gold.ordance.board.model.api.domain.address.WebAddress;
import ru.gold.ordance.board.model.entity.domain.Address;

public interface AddressMapper {
    Address fromRequest(AddressUpdateRq rq);

    WebAddress fromEntity(Address entity);
}

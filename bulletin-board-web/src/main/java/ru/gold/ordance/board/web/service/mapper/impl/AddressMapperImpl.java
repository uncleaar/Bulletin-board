package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.web.api.address.AddressUpdateRq;
import ru.gold.ordance.board.web.api.address.WebAddress;
import ru.gold.ordance.board.core.entity.Address;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Street;
import ru.gold.ordance.board.web.service.mapper.AddressMapper;

public class AddressMapperImpl implements AddressMapper {
    @Override
    public Address fromRequest(AddressUpdateRq rq) {
        return Address.builder()
                .id(rq.getEntityId())
                .locality(Locality.builder()
                        .id(rq.getLocalityId())
                        .build())
                .street(Street.builder()
                        .id(rq.getStreetId())
                        .build())
                .houseNumber(rq.getHouseNumber())
                .build();
    }

    @Override
    public WebAddress fromEntity(Address entity) {
        return WebAddress.builder()
                .entityId(entity.getId())
                .localityId(entity.getLocality().getId())
                .streetId(entity.getStreet().getId())
                .houseNumber(entity.getHouseNumber())
                .build();
    }
}

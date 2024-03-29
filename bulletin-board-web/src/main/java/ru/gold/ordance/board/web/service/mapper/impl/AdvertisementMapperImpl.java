package ru.gold.ordance.board.web.service.mapper.impl;

import ru.gold.ordance.board.core.entity.*;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.web.api.advertisement.WebAdvertisement;
import ru.gold.ordance.board.web.service.mapper.AdvertisementMapper;

public class AdvertisementMapperImpl implements AdvertisementMapper {
    @Override
    public Advertisement fromRequest(AdvertisementUpdateRq rq) {
        return Advertisement.builder()
                .id(rq.getEntityId())
                .client(Client.builder()
                        .id(rq.getClientId())
                        .build())
                .name(rq.getName())
                .subcategory(Subcategory.builder()
                        .id(rq.getSubcategoryId())
                        .build())
                .description(rq.getDescription())
                .price(rq.getPrice())
                .locality(Locality.builder()
                        .id(rq.getLocalityId())
                        .build())
                .street(Street.builder()
                        .id(rq.getStreetId())
                        .build())
                .houseNumber(rq.getHouseNumber())
                .photo(Photo.builder()
                        .id(rq.getPhotoId())
                        .build())
                .build();
    }

    @Override
    public WebAdvertisement fromEntity(Advertisement entity) {
        return WebAdvertisement.builder()
                .entityId(entity.getId())
                .clientId(entity.getClient().getId())
                .name(entity.getName())
                .createDate(entity.getCreateDate())
                .subcategoryId(entity.getSubcategory().getId())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .localityId(entity.getLocality().getId())
                .streetId(entity.getStreet().getId())
                .houseNumber(entity.getHouseNumber())
                .photoId(entity.getPhoto().getId())
                .build();
    }
}

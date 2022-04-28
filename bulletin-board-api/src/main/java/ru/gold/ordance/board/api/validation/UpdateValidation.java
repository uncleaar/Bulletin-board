package ru.gold.ordance.board.api.validation;

import ru.gold.ordance.board.model.api.domain.UpdateRq;
import ru.gold.ordance.board.model.api.domain.address.AddressUpdateRq;
import ru.gold.ordance.board.model.api.domain.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.model.api.domain.category.CategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.client.ClientUpdateRq;
import ru.gold.ordance.board.model.api.domain.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.model.api.domain.lnk.LnkLocalityStreetUpdateRq;
import ru.gold.ordance.board.model.api.domain.locality.LocalityUpdateRq;
import ru.gold.ordance.board.model.api.domain.region.RegionUpdateRq;
import ru.gold.ordance.board.model.api.domain.street.StreetUpdateRq;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryUpdateRq;

import static java.util.Objects.nonNull;
import static ru.gold.ordance.board.api.validation.ValidationHelper.*;

class UpdateValidation implements RequestValidation<UpdateRq> {
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    @Override
    public void validate(UpdateRq rq) {
        if (rq instanceof SubcategoryUpdateRq) {
            validateRequest((SubcategoryUpdateRq) rq);
        } else if (rq instanceof StreetUpdateRq) {
            validateRequest((StreetUpdateRq) rq);
        } else if (rq instanceof RegionUpdateRq) {
            validateRequest((RegionUpdateRq) rq);
        } else if (rq instanceof LocalityUpdateRq) {
            validateRequest((LocalityUpdateRq) rq);
        } else if (rq instanceof LnkLocalityStreetUpdateRq) {
            validateRequest((LnkLocalityStreetUpdateRq) rq);
        } else if (rq instanceof ClientUpdateRq) {
            validateRequest((ClientUpdateRq) rq);
        } else if (rq instanceof CategoryUpdateRq) {
            validateRequest((CategoryUpdateRq) rq);
        } else if (rq instanceof AdvertisementUpdateRq) {
            validateRequest((AdvertisementUpdateRq) rq);
        } else if (rq instanceof AddressUpdateRq) {
            validateRequest((AddressUpdateRq) rq);
        } else if (rq instanceof ComplexAddressUpdateRq) {
            validateRequest((ComplexAddressUpdateRq) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(SubcategoryUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorName(rq.getName());
        errorObjectId(rq.getCategoryId(), "categoryId");
    }

    private void validateRequest(StreetUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorName(rq.getName());
    }


    private void validateRequest(RegionUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorName(rq.getName());
    }

    private void validateRequest(LocalityUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorName(rq.getName());
        errorObjectId(rq.getRegionId(), "regionId");
    }

    private void validateRequest(LnkLocalityStreetUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorObjectId(rq.getLocalityId(), "localityId");
        errorObjectId(rq.getStreetId(), "streetId");
    }

    private void validateRequest(ClientUpdateRq rq) {
        errorObjectId(rq.getEntityId(), "entityId");
        errorString(rq.getPassword(), "password");
        errorName(rq.getName());
        errorString(rq.getPhoneNumber(), "phoneNumber");
    }

    private void validateRequest(CategoryUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorName(rq.getName());
    }

    private void validateRequest(AdvertisementUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorObjectId(rq.getClientId(), "clientId");
        errorName(rq.getName());
        errorObjectId(rq.getSubcategoryId(), "subcategoryId");

        if (nonNull(rq.getDescription()) && rq.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            error("The description length is very long.");
        }

        if (rq.getPrice() < 0) {
            error("The price is negative.");
        }

        errorObjectId(rq.getLocalityId(), "localityId");
        errorObjectId(rq.getStreetId(), "streetId");
        errorString(rq.getHouseNumber(), "houseNumber");
    }

    private void validateRequest(AddressUpdateRq rq) {
        errorEntityId(rq.getEntityId());
        errorObjectId(rq.getLocalityId(), "localityId");
        errorObjectId(rq.getStreetId(), "streetId");
        errorString(rq.getHouseNumber(), "houseNumber");
    }

    private void validateRequest(ComplexAddressUpdateRq rq) {
        errorString(rq.getRegionName(), "regionName");
        errorString(rq.getLocalityName(), "localityName");
        errorString(rq.getStreetName(), "streetName");
        errorString(rq.getHouseNumber(), "houseNumber");
    }
}

package ru.gold.ordance.board.api.validation;

import ru.gold.ordance.board.model.api.domain.GetByIdRq;
import ru.gold.ordance.board.model.api.domain.GetByNameRq;
import ru.gold.ordance.board.model.api.domain.GetRq;
import ru.gold.ordance.board.model.api.domain.client.ClientGetByLoginRq;
import ru.gold.ordance.board.model.api.domain.lnk.LnkLocalityStreetGetByLSRq;
import ru.gold.ordance.board.model.api.domain.lnk.LnkLocalityStreetGetByLocalityRq;
import ru.gold.ordance.board.model.api.domain.lnk.LnkLocalityStreetGetByStreetRq;

import static ru.gold.ordance.board.api.validation.ValidationHelper.*;

class GetValidation implements RequestValidation<GetRq> {
    @Override
    public void validate(GetRq rq) {
        if (rq instanceof GetByIdRq) {
            errorObjectId(((GetByIdRq) rq).getEntityId(), "entityId");
        } else if (rq instanceof GetByNameRq) {
            errorName(((GetByNameRq) rq).getName());
        } else {
            if (rq instanceof LnkLocalityStreetGetByStreetRq) {
                validateRequest((LnkLocalityStreetGetByStreetRq) rq);
            } else if (rq instanceof LnkLocalityStreetGetByLocalityRq) {
                validateRequest((LnkLocalityStreetGetByLocalityRq) rq);
            } else if (rq instanceof LnkLocalityStreetGetByLSRq) {
               validateRequest((LnkLocalityStreetGetByLSRq) rq);
            } else if (rq instanceof ClientGetByLoginRq) {
                validateRequest((ClientGetByLoginRq) rq);
            } else {
                throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
            }
        }
    }

    private void validateRequest(LnkLocalityStreetGetByStreetRq rq) {
        errorObjectId(rq.getStreetId(), "streetId");
    }

    private void validateRequest(LnkLocalityStreetGetByLocalityRq rq) {
        errorObjectId(rq.getLocalityId(), "localityId");
    }

    private void validateRequest(LnkLocalityStreetGetByLSRq rq) {
        errorObjectId(rq.getStreetId(), "streetId");
        errorObjectId(rq.getLocalityId(), "localityId");
    }

    private void validateRequest(ClientGetByLoginRq rq) {
        errorString(rq.getLogin(), "login");
    }
}

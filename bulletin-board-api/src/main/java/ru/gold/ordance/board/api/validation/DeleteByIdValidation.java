package ru.gold.ordance.board.api.validation;

import ru.gold.ordance.board.model.api.domain.DeleteByIdRq;

import static ru.gold.ordance.board.api.validation.ValidationHelper.errorObjectId;

class DeleteByIdValidation implements RequestValidation<DeleteByIdRq> {
    @Override
    public void validate(DeleteByIdRq rq) {
        errorObjectId(rq.getEntityId(), "entityId");
    }
}

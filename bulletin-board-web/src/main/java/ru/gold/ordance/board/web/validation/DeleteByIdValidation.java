package ru.gold.ordance.board.web.validation;

import ru.gold.ordance.board.web.api.DeleteByIdRq;

import static ru.gold.ordance.board.web.validation.ValidationHelper.errorObjectId;

class DeleteByIdValidation implements RequestValidation<DeleteByIdRq> {
    @Override
    public void validate(DeleteByIdRq rq) {
        errorObjectId(rq.getEntityId(), "entityId");
    }
}

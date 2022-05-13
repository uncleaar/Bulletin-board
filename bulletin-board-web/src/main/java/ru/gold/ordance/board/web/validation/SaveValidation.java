package ru.gold.ordance.board.web.validation;

import ru.gold.ordance.board.web.api.SaveRq;
import ru.gold.ordance.board.web.api.client.ClientSaveRq;

import static ru.gold.ordance.board.web.validation.ValidationHelper.errorName;
import static ru.gold.ordance.board.web.validation.ValidationHelper.errorString;

class SaveValidation implements RequestValidation<SaveRq> {
    @Override
    public void validate(SaveRq rq) {
        if (rq instanceof ClientSaveRq) {
            validateRequest((ClientSaveRq) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(ClientSaveRq rq) {
        errorString(rq.getLogin(), "login");
        errorString(rq.getPassword(), "password");
        errorName(rq.getName());
        errorString(rq.getPhoneNumber(), "phoneNumber");
    }
}

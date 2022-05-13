package ru.gold.ordance.board.web.validation;

import ru.gold.ordance.board.web.api.AuthRq;
import ru.gold.ordance.board.web.api.auth.AuthLoginRq;
import ru.gold.ordance.board.web.api.auth.AuthRegistrationRq;
import ru.gold.ordance.board.web.api.auth.AuthTokenLifeRq;

import static ru.gold.ordance.board.web.validation.ValidationHelper.errorString;

public class AuthValidation implements RequestValidation<AuthRq>{
    @Override
    public void validate(AuthRq rq) {
        if (rq instanceof AuthLoginRq) {
            validateRequest((AuthLoginRq) rq);
        } else if (rq instanceof AuthRegistrationRq) {
            validateRequest((AuthRegistrationRq) rq);
        } else if (rq instanceof AuthTokenLifeRq) {
            validateRequest((AuthTokenLifeRq) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(AuthLoginRq rq) {
        errorString(rq.getLogin(), "login");
        errorString(rq.getPassword(), "password");
    }

    private void validateRequest(AuthRegistrationRq rq) {
        errorString(rq.getLogin(), "login");
        errorString(rq.getPassword(), "password");
        errorString(rq.getName(), "name");
        errorString(rq.getPhoneNumber(), "phoneNumber");
    }

    private void validateRequest(AuthTokenLifeRq rq) {
        errorString(rq.getToken(), "token");
    }
}

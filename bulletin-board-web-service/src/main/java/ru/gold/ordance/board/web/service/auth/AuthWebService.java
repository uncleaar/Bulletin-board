package ru.gold.ordance.board.web.service.auth;

import ru.gold.ordance.board.model.api.domain.auth.*;

public interface AuthWebService {
    AuthLoginRs login(AuthLoginRq rq);
    AuthRegistrationRs registration(AuthRegistrationRq rq);
    AuthTokenLifeRs validationToken(AuthTokenLifeRq rq);
}

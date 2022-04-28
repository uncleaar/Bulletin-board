package ru.gold.ordance.board.api.rest.heir.auth;

import ru.gold.ordance.board.model.api.domain.auth.*;

public interface AuthenticationRestController {
    AuthLoginRs login(AuthLoginRq rq);
    AuthRegistrationRs registration(AuthRegistrationRq rq);
    AuthTokenLifeRs validationToken(String token);
}

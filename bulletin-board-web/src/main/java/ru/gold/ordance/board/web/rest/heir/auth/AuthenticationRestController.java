package ru.gold.ordance.board.web.rest.heir.auth;

import ru.gold.ordance.board.web.api.auth.*;

public interface AuthenticationRestController {
    AuthLoginRs login(AuthLoginRq rq);
    AuthRegistrationRs registration(AuthRegistrationRq rq);
    AuthTokenLifeRs validationToken(String token);
}

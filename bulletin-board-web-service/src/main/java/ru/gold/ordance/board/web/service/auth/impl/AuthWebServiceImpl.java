package ru.gold.ordance.board.web.service.auth.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.auth.*;
import ru.gold.ordance.board.model.entity.domain.Client;
import ru.gold.ordance.board.service.base.heir.ClientService;
import ru.gold.ordance.board.web.service.auth.AuthWebService;
import ru.gold.ordance.board.web.service.auth.jwt.JwtTokenProvider;

import java.util.Optional;

@Service
public class AuthWebServiceImpl implements AuthWebService {

    private final ClientService service;

    private final AuthenticationManager manager;

    private final JwtTokenProvider provider;

    public AuthWebServiceImpl(ClientService service, AuthenticationManager manager, JwtTokenProvider provider) {
        this.service = service;
        this.manager = manager;
        this.provider = provider;
    }

    @Override
    public AuthLoginRs login(AuthLoginRq rq) {
        String login = rq.getLogin();

        manager.authenticate(new UsernamePasswordAuthenticationToken(login, rq.getPassword()));

        Optional<Client> user = service.findByLogin(login);
        String token = provider.createJwt(login, user.get().getRole().getName());

        return AuthLoginRs.success(token, user.get().getRole().getName());
    }

    @Override
    public AuthRegistrationRs registration(AuthRegistrationRq rq) {
        service.update(Client.builder()
                .login(rq.getLogin())
                .password(rq.getPassword())
                .name(rq.getName())
                .phoneNumber(rq.getPhoneNumber())
                .build());

        return AuthRegistrationRs.success();
    }

    @Override
    public AuthTokenLifeRs validationToken(AuthTokenLifeRq rq) {
        boolean isValid = provider.validateJwtToken(rq.getToken());

        return AuthTokenLifeRs.success(isValid);
    }
}

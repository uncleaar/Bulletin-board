package ru.gold.ordance.board.web.service.auth.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.gold.ordance.board.core.entity.Role;
import ru.gold.ordance.board.web.api.auth.*;
import ru.gold.ordance.board.core.entity.Client;
import ru.gold.ordance.board.core.service.heir.ClientService;
import ru.gold.ordance.board.web.api.client.WebClient;
import ru.gold.ordance.board.web.service.auth.AuthWebService;
import ru.gold.ordance.board.web.service.auth.jwt.JwtTokenProvider;
import ru.gold.ordance.board.web.service.mapper.ClientMapper;
import ru.gold.ordance.board.web.service.mapper.impl.ClientMapperImpl;

import java.util.Optional;

@Service
public class AuthWebServiceImpl implements AuthWebService {

    private final ClientService service;

    private final AuthenticationManager manager;

    private final JwtTokenProvider provider;

    private final ClientMapper mapper = new ClientMapperImpl();

    public AuthWebServiceImpl(ClientService service,
                              AuthenticationManager manager,
                              JwtTokenProvider provider) {
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
        Optional<Client> updatedClient = service.update(Client.builder()
                .login(rq.getLogin())
                .password(rq.getPassword())
                .name(rq.getName())
                .phoneNumber(rq.getPhoneNumber())
                .build());

        if (updatedClient.isPresent()) {
            String token = provider.createJwt(rq.getLogin(), Role.USER.getName());
            return AuthRegistrationRs.success(mapper.fromEntity(updatedClient.get()), token);
        }

        return AuthRegistrationRs.success(null, null);
    }

    @Override
    public AuthTokenLifeRs validationToken(AuthTokenLifeRq rq) {
        boolean isValid = provider.validateJwtToken(rq.getToken());

        return AuthTokenLifeRs.success(isValid);
    }
}

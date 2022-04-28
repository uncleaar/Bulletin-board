package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.client.*;
import ru.gold.ordance.board.model.api.domain.street.StreetDeleteByIdRs;
import ru.gold.ordance.board.model.entity.domain.Client;
import ru.gold.ordance.board.service.base.heir.ClientService;
import ru.gold.ordance.board.web.service.base.ClientWebService;
import ru.gold.ordance.board.web.service.mapper.ClientMapper;
import ru.gold.ordance.board.web.service.mapper.impl.ClientMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientWebServiceImpl implements ClientWebService {
    private final ClientService service;

    private final ClientMapper mapper = new ClientMapperImpl();

    public ClientWebServiceImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public ClientGetRs findAll() {
        List<Client> found = service.findAll();

        return list(found);
    }

    @Override
    public ClientGetRs findById(ClientGetByIdRq rq) {
        Optional<Client> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public ClientGetRs findByName(ClientGetByNameRq rq) {
        List<Client> found = service.findAllByName(rq.getName());

        return list(found);
    }

    @Override
    public ClientGetRs findByLogin(ClientGetByLoginRq rq) {
        Optional<Client> found = service.findByLogin(rq.getLogin());

        return searchResult(found);
    }

    @Override
    public ClientSaveRs save(ClientSaveRq rq) {
        Optional<Client> saved = service.update(mapper.fromRequest(rq));
        boolean isPresent = saved.isPresent();

        return ClientSaveRs.success(isPresent ? saved.get().getId() : null);
    }

    @Override
    public ClientUpdateRs update(ClientUpdateRq rq) {
        Optional<Client> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return ClientUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public ClientDeleteByIdRs deleteById(ClientDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return ClientDeleteByIdRs.success();
    }

    private ClientGetRs list(List<Client> list) {
        if (!list.isEmpty()) {
            List<WebClient> webClients = list.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return ClientGetRs.success(webClients);
        } else {
            return ClientGetRs.success(Collections.emptyList());
        }
    }

    private ClientGetRs searchResult(Optional<Client> found) {
        if (found.isPresent()) {
            WebClient webClient = mapper.fromEntity(found.get());

            return ClientGetRs.success(Collections.singletonList(webClient));
        } else {
            return ClientGetRs.success(Collections.emptyList());
        }
    }
}

package ru.gold.ordance.board.service.base.heir;

import ru.gold.ordance.board.model.entity.domain.Client;
import ru.gold.ordance.board.service.base.SearchAllByNameService;

import java.util.Optional;

public interface ClientService extends SearchAllByNameService<Client> {
    Optional<Client> findByLogin(String login);
}

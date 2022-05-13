package ru.gold.ordance.board.core.service.heir;

import ru.gold.ordance.board.core.entity.Client;
import ru.gold.ordance.board.core.service.SearchAllByNameService;

import java.util.Optional;

public interface ClientService extends SearchAllByNameService<Client> {
    Optional<Client> findByLogin(String login);
}

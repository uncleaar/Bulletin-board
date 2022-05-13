package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.Client;
import ru.gold.ordance.board.core.persistence.SearchByNameRepository;

import java.util.Optional;

@Repository
public interface ClientRepository extends SearchByNameRepository<Client> {
    Optional<Client> findByLogin(String login);
}

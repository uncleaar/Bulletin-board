package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.domain.Client;
import ru.gold.ordance.board.persistence.repository.SearchByNameRepository;

import java.util.Optional;

@Repository
public interface ClientRepository extends SearchByNameRepository<Client> {
    Optional<Client> findByLogin(String login);
}

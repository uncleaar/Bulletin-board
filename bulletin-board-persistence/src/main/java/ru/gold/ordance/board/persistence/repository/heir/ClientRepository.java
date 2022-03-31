package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.authorization.Client;
import ru.gold.ordance.board.persistence.repository.SearchAllByNameRepository;

@Repository
public interface ClientRepository extends SearchAllByNameRepository<Client> {
}

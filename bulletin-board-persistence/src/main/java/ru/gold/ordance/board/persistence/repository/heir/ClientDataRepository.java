package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.authorization.ClientData;
import ru.gold.ordance.board.persistence.repository.SearchAllByNameRepository;

@Repository
public interface ClientDataRepository extends SearchAllByNameRepository<ClientData> {
}

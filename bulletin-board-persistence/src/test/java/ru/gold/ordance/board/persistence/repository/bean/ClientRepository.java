package ru.gold.ordance.board.persistence.repository.bean;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.authorization.Client;
import ru.gold.ordance.board.persistence.repository.EntityRepository;

@Repository
public interface ClientRepository extends EntityRepository<Client> {
}

package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.domain.Address;
import ru.gold.ordance.board.persistence.repository.EntityRepository;

@Repository
public interface AddressRepository extends EntityRepository<Address> {
}

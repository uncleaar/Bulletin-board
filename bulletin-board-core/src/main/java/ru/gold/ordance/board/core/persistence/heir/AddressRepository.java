package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.Address;
import ru.gold.ordance.board.core.persistence.EntityRepository;

@Repository
public interface AddressRepository extends EntityRepository<Address> {
}

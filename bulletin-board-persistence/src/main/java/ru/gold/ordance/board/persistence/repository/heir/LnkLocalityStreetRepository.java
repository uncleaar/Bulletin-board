package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.domain.LnkLocalityStreet;
import ru.gold.ordance.board.model.domain.Locality;
import ru.gold.ordance.board.model.domain.Street;
import ru.gold.ordance.board.persistence.repository.EntityRepository;

import java.util.List;

@Repository
public interface LnkLocalityStreetRepository extends EntityRepository<LnkLocalityStreet> {
    List<LnkLocalityStreet> findByLocalityOrStreet(Locality locality, Street street);
}

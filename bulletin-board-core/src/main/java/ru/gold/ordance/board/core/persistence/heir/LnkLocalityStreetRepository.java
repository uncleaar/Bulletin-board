package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.LnkLocalityStreet;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Street;
import ru.gold.ordance.board.core.persistence.EntityRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LnkLocalityStreetRepository extends EntityRepository<LnkLocalityStreet> {
    Optional<LnkLocalityStreet> findByLocalityAndStreet(Locality locality, Street street);
    List<LnkLocalityStreet> findByLocality(Locality locality);
    List<LnkLocalityStreet> findByStreet(Street street);
}

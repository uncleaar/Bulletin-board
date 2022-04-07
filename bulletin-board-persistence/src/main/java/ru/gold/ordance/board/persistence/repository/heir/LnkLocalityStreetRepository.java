package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.domain.LnkLocalityStreet;
import ru.gold.ordance.board.model.entity.domain.Locality;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.persistence.repository.EntityRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LnkLocalityStreetRepository extends EntityRepository<LnkLocalityStreet> {
    Optional<LnkLocalityStreet> findByLocalityAndStreet(Locality locality, Street street);
    List<LnkLocalityStreet> findByLocality(Locality locality);
    List<LnkLocalityStreet> findByStreet(Street street);
}

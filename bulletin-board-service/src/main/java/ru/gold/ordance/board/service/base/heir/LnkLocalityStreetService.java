package ru.gold.ordance.board.service.base.heir;

import ru.gold.ordance.board.model.entity.domain.LnkLocalityStreet;
import ru.gold.ordance.board.model.entity.domain.Locality;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.service.base.AbstractService;

import java.util.List;
import java.util.Optional;

public interface LnkLocalityStreetService extends AbstractService<LnkLocalityStreet> {
    Optional<LnkLocalityStreet> findByLocalityAndStreet(Locality locality, Street street);
    List<LnkLocalityStreet> findByLocality(Locality locality);
    List<LnkLocalityStreet> findByStreet(Street street);
}

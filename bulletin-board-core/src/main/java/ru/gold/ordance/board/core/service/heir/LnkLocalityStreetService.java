package ru.gold.ordance.board.core.service.heir;

import ru.gold.ordance.board.core.entity.LnkLocalityStreet;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Street;
import ru.gold.ordance.board.core.service.AbstractService;

import java.util.List;
import java.util.Optional;

public interface LnkLocalityStreetService extends AbstractService<LnkLocalityStreet> {
    Optional<LnkLocalityStreet> findByLocalityAndStreet(Locality locality, Street street);
    List<LnkLocalityStreet> findByLocality(Locality locality);
    List<LnkLocalityStreet> findByStreet(Street street);
}

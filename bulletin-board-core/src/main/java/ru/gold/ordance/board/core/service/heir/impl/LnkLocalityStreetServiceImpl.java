package ru.gold.ordance.board.core.service.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.core.entity.LnkLocalityStreet;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Street;
import ru.gold.ordance.board.core.persistence.heir.LnkLocalityStreetRepository;
import ru.gold.ordance.board.core.utils.StorageHelper;
import ru.gold.ordance.board.core.service.heir.LnkLocalityStreetService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class LnkLocalityStreetServiceImpl implements LnkLocalityStreetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LnkLocalityStreetServiceImpl.class);

    private final LnkLocalityStreetRepository repository;

    private final StorageHelper helper;

    public LnkLocalityStreetServiceImpl(LnkLocalityStreetRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<LnkLocalityStreet> findAll() {
        LOGGER.info("The search for all link locality-street has started.");

        List<LnkLocalityStreet> lnkLocalityStreets = repository.findAll();

        LOGGER.info("Size of list: {}", lnkLocalityStreets.size());

        return lnkLocalityStreets;
    }

    @Override
    public @Nullable Optional<LnkLocalityStreet> findById(@NotNull Long id) {
        LOGGER.info("The search by id of link locality-street has started.");

        Optional<LnkLocalityStreet> lnkLocalityStreet = repository.findById(id);

        if (lnkLocalityStreet.isEmpty()) {
            LOGGER.info("The link locality-street not found. entityId = {}", id);
        } else {
            LOGGER.info("The link locality-street was found. link locality-street = {}", lnkLocalityStreet.get());
        }

        return lnkLocalityStreet;
    }

    @Override
    public @Nullable Optional<LnkLocalityStreet> findByLocalityAndStreet(@NotNull Locality locality, @NotNull Street street) {
        LOGGER.info("The search by locality and street of link locality-street has started.");

        Optional<LnkLocalityStreet> found = repository.findByLocalityAndStreet(locality, street);

        if (found.isEmpty()) {
            LOGGER.info("The link locality-street not found. locality = {}, street = {}", locality, street);
        } else {
            LOGGER.info("The link locality-street was found. locality = {}, street = {}", locality, street);
        }

        return found;
    }

    @Override
    public List<LnkLocalityStreet> findByLocality(@NotNull Locality locality) {
        LOGGER.info("The search by locality of link locality-street has started.");

        List<LnkLocalityStreet> found = repository.findByLocality(locality);

        LOGGER.info("Size of list: {}", found.size());

        return found;
    }

    @Override
    public List<LnkLocalityStreet> findByStreet(Street street) {
        LOGGER.info("The search by street of link locality-street has started.");

        List<LnkLocalityStreet> found = repository.findByStreet(street);

        LOGGER.info("Size of list: {}", found.size());

        return found;
    }

    @Override
    public @Nullable Optional<LnkLocalityStreet> update(@NotNull LnkLocalityStreet lnkLocalityStreet) {
        LOGGER.info("Update link locality-street has started.");

        boolean exists = helper.exists(lnkLocalityStreet);

        if (!helper.existsExternal(lnkLocalityStreet)) {
            LOGGER.info("Link locality-street " + (exists ? "save" : "update") + " is ignored. link locality-street = {}", lnkLocalityStreet);

            return Optional.empty();
        }

        if (!exists && lnkLocalityStreet.getId() != null) {
            LOGGER.info("The lnkLocalityStreet does not exist by the passed id. lnkLocalityStreet = {}", lnkLocalityStreet);

            return Optional.empty();
        }

        LnkLocalityStreet updatedLnkLocalityStreet = repository.saveAndFlush(lnkLocalityStreet);

        if (exists) {
            LOGGER.info("The link locality-street was updated. link locality-street = {}", updatedLnkLocalityStreet);
        } else {
            LOGGER.info("The link locality-street was saved. link locality-street = {}", updatedLnkLocalityStreet);
        }

        return Optional.of(updatedLnkLocalityStreet);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete link locality-street has started.");

        Optional<LnkLocalityStreet> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The link locality-street was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The link locality-street does not exist. entityId = {}", id);
        }
    }
}

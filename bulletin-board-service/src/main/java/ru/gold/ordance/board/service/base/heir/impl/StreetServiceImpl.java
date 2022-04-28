package ru.gold.ordance.board.service.base.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.persistence.repository.heir.StreetRepository;
import ru.gold.ordance.board.persistence.utils.StorageHelper;
import ru.gold.ordance.board.service.base.heir.StreetService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class StreetServiceImpl implements StreetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreetServiceImpl.class);

    private final StreetRepository repository;

    private final StorageHelper helper;

    public StreetServiceImpl(StreetRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Street> findAll() {
        LOGGER.info("The search for all streets has started.");

        List<Street> streets = repository.findAll();

        LOGGER.info("Size of list: {}", streets.size());

        return streets;
    }

    @Override
    public @Nullable Optional<Street> findById(@NotNull Long id) {
        LOGGER.info("The search by id street has started.");

        Optional<Street> street = repository.findById(id);

        if (street.isEmpty()) {
            LOGGER.info("The street not found. entityId = {}", id);
        } else {
            LOGGER.info("The street was found. street = {}", street.get());
        }

        return street;
    }

    @Override
    public @Nullable Optional<Street> findByName(@NotNull String name) {
        LOGGER.info("The search by name street has started.");

        Optional<Street> found = repository.findByNameIgnoreCase(name);

        if (found.isEmpty()) {
            LOGGER.info("The street not found. name = {}", name);
        } else {
            LOGGER.info("The street was found. street = {}", found.get());
        }

        return found;
    }

    @Override
    public @Nullable Optional<Street> update(@NotNull Street street) {
        LOGGER.info("Update street has started.");

        boolean exists = helper.exists(street);
        if (!exists && street.getId() != null) {
            LOGGER.info("The street does not exist by the passed id. street = {}", street);

            return Optional.empty();
        }

        Street updatedStreet = repository.saveAndFlush(street);

        if (exists) {
            LOGGER.info("The street was updated. street = {}", updatedStreet);
        } else {
            LOGGER.info("The street was saved. street = {}", updatedStreet);
        }

        return Optional.of(updatedStreet);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete street has started.");

        Optional<Street> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The street was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The street does not exist. entityId = {}", id);
        }
    }
}

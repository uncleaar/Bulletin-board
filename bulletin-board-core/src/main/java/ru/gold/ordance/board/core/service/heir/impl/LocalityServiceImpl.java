package ru.gold.ordance.board.core.service.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.persistence.heir.LocalityRepository;
import ru.gold.ordance.board.core.utils.StorageHelper;
import ru.gold.ordance.board.core.service.heir.LocalityService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class LocalityServiceImpl implements LocalityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalityServiceImpl.class);

    private final LocalityRepository repository;

    private final StorageHelper helper;

    public LocalityServiceImpl(LocalityRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Locality> findAll() {
        LOGGER.info("The search for all localities has started.");

        List<Locality> localities = repository.findAll();

        LOGGER.info("Size of list: {}", localities.size());

        return localities;
    }

    @Override
    public @Nullable Optional<Locality> findById(@NotNull Long id) {
        LOGGER.info("The search by id locality has started.");

        Optional<Locality> locality = repository.findById(id);

        if (locality.isEmpty()) {
            LOGGER.info("The locality not found. entityId = {}", id);
        } else {
            LOGGER.info("The locality was found. locality = {}", locality.get());
        }

        return locality;
    }

    @Override
    public Optional<Locality> findByName(@NotNull String name) {
        LOGGER.info("The search by name localities has started.");

        Optional<Locality> found = repository.findByNameIgnoreCase(name);

        if (found.isEmpty()) {
            LOGGER.info("The locality not found. name = {}", name);
        } else {
            LOGGER.info("The locality was found. locality = {}", found.get());
        }

        return found;
    }

    @Override
    public @Nullable Optional<Locality> update(@NotNull Locality locality) {
        LOGGER.info("Update locality has started.");

        boolean exists = helper.exists(locality);

        if (!helper.existsExternal(locality)) {
            LOGGER.info("Locality " + (exists ? "save" : "update") + " is ignored. locality = {}", locality);

            return Optional.empty();
        }

        if (!exists && locality.getId() != null) {
            LOGGER.info("The locality does not exist by the passed id. locality = {}", locality);

            return Optional.empty();
        }

        Locality updatedLocality = repository.saveAndFlush(locality);

        if (exists) {
            LOGGER.info("The locality was updated. locality = {}", updatedLocality);
        } else {
            LOGGER.info("The locality was saved. locality = {}", updatedLocality);
        }

        return Optional.of(updatedLocality);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete locality has started.");

        Optional<Locality> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The locality was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The locality does not exist. entityId = {}", id);
        }
    }
}

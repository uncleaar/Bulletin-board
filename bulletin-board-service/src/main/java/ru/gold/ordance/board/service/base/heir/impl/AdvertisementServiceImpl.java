package ru.gold.ordance.board.service.base.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.model.entity.domain.Advertisement;
import ru.gold.ordance.board.persistence.repository.heir.AdvertisementRepository;
import ru.gold.ordance.board.persistence.utils.StorageHelper;
import ru.gold.ordance.board.service.base.heir.AdvertisementService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AdvertisementServiceImpl implements AdvertisementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementServiceImpl.class);

    private final AdvertisementRepository repository;

    private final StorageHelper helper;

    public AdvertisementServiceImpl(AdvertisementRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Advertisement> findAll() {
        LOGGER.info("The search for all advertisements has started.");

        List<Advertisement> advertisements = repository.findAll();

        LOGGER.info("Size of list: {}", advertisements.size());

        return advertisements;
    }

    @Override
    public @Nullable Optional<Advertisement> findById(@NotNull Long id) {
        LOGGER.info("The search by id advertisements has started.");

        Optional<Advertisement> advertisement = repository.findById(id);

        if (advertisement.isEmpty()) {
            LOGGER.info("The advertisement not found. entityId = {}", id);
        } else {
            LOGGER.info("The advertisement was found. advertisement = {}", advertisement.get());
        }

        return advertisement;
    }

    @Override
    public @Nullable Optional<Advertisement> update(@NotNull Advertisement advertisement) {
        LOGGER.info("Update advertisement has started.");

        boolean exists = helper.exists(advertisement);

        if (!helper.existsExternal(advertisement)) {
            LOGGER.info("Advertisement " + (exists ? "save" : "update") + " is ignored. advertisement = {}", advertisement);

            return Optional.empty();
        }

        Advertisement updatedAdvertisement = repository.saveAndFlush(advertisement);

        if (exists) {
            LOGGER.info("The advertisement was updated. advertisement = {}", updatedAdvertisement);
        } else {
            LOGGER.info("The advertisement was saved. advertisement = {}", updatedAdvertisement);
        }

        return Optional.of(updatedAdvertisement);
    }

    @Override
    public void delete(@NotNull Advertisement advertisement) {
        deleteById(advertisement.getId());
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete advertisement has started.");

        Optional<Advertisement> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The advertisement was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The advertisement does not exist. entityId = {}", id);
        }
    }

    @Override
    public List<Advertisement> findAllByName(@NotNull String name) {
        LOGGER.info("The search by name advertisements has started.");

        List<Advertisement> found = repository.findByNameIgnoreCaseContaining(name);

        LOGGER.info("Size of list: {}", found.size());

        return found;
    }
}
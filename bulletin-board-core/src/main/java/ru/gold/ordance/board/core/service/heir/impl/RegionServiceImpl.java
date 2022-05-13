package ru.gold.ordance.board.core.service.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.core.entity.Region;
import ru.gold.ordance.board.core.persistence.heir.RegionRepository;
import ru.gold.ordance.board.core.utils.StorageHelper;
import ru.gold.ordance.board.core.service.heir.RegionService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class RegionServiceImpl implements RegionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionServiceImpl.class);

    private final RegionRepository repository;

    private final StorageHelper helper;

    public RegionServiceImpl(RegionRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Region> findAll() {
        LOGGER.info("The search for all regions has started.");

        List<Region> regions = repository.findAll();

        LOGGER.info("Size of list: {}", regions.size());

        return regions;
    }

    @Override
    public @Nullable Optional<Region> findById(@NotNull Long id) {
        LOGGER.info("The search by id region has started.");

        Optional<Region> region = repository.findById(id);

        if (region.isEmpty()) {
            LOGGER.info("The region not found. entityId = {}", id);
        } else {
            LOGGER.info("The region was found. region = {}", region.get());
        }

        return region;
    }

    @Override
    public @Nullable Optional<Region> findByName(@NotNull String name) {
        LOGGER.info("The search by name region has started.");

        Optional<Region> found = repository.findByNameIgnoreCase(name);

        if (found.isEmpty()) {
            LOGGER.info("The region not found. name = {}", name);
        } else {
            LOGGER.info("The region was found. region = {}", found.get());
        }

        return found;
    }

    @Override
    public @Nullable Optional<Region> update(@NotNull Region region) {
        LOGGER.info("Update region has started.");

        boolean exists = helper.exists(region);
        if (!exists && region.getId() != null) {
            LOGGER.info("The region does not exist by the passed id. region = {}", region);

            return Optional.empty();
        }

        Region updatedRegion = repository.saveAndFlush(region);

        if (exists) {
            LOGGER.info("The region was updated. region = {}", updatedRegion);
        } else {
            LOGGER.info("The region was saved. region = {}", updatedRegion);
        }

        return Optional.of(updatedRegion);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete region has started.");

        Optional<Region> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The region was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The region does not exist. entityId = {}", id);
        }
    }
}

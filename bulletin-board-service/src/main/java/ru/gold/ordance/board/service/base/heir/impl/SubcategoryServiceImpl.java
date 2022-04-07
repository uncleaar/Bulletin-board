package ru.gold.ordance.board.service.base.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.model.entity.domain.Subcategory;
import ru.gold.ordance.board.persistence.repository.heir.SubcategoryRepository;
import ru.gold.ordance.board.persistence.utils.StorageHelper;
import ru.gold.ordance.board.service.base.heir.SubcategoryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class SubcategoryServiceImpl implements SubcategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubcategoryServiceImpl.class);

    private final SubcategoryRepository repository;

    private final StorageHelper helper;

    public SubcategoryServiceImpl(SubcategoryRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Subcategory> findAll() {
        LOGGER.info("The search for all subcategories has started.");

        List<Subcategory> subcategories = repository.findAll();

        LOGGER.info("Size of list: {}", subcategories.size());

        return subcategories;
    }

    @Override
    public @Nullable Optional<Subcategory> findById(@NotNull Long id) {
        LOGGER.info("The search by id subcategory has started.");

        Optional<Subcategory> subcategory = repository.findById(id);

        if (subcategory.isEmpty()) {
            LOGGER.info("The subcategory not found. entityId = {}", id);
        } else {
            LOGGER.info("The subcategory was found. subcategory = {}", subcategory.get());
        }

        return subcategory;
    }

    @Override
    public @Nullable Optional<Subcategory> update(@NotNull Subcategory subcategory) {
        LOGGER.info("Update subcategory has started.");

        boolean exists = helper.exists(subcategory);

        if (!helper.existsExternal(subcategory)) {
            LOGGER.info("Subcategory " + (exists ? "save" : "update") + " is ignored. subcategory = {}", subcategory);

            return Optional.empty();
        }

        Subcategory updatedSubcategory = repository.saveAndFlush(subcategory);

        if (exists) {
            LOGGER.info("The subcategory was updated. subcategory = {}", updatedSubcategory);
        } else {
            LOGGER.info("The subcategory was saved. subcategory = {}", updatedSubcategory);
        }

        return Optional.of(updatedSubcategory);
    }

    @Override
    public void delete(@NotNull Subcategory subcategory) {
        deleteById(subcategory.getId());
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete subcategory has started.");

        Optional<Subcategory> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The subcategory was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The subcategory does not exist. entityId = {}", id);
        }
    }

    @Override
    public @Nullable Optional<Subcategory> findByName(@NotNull String name) {
        LOGGER.info("The search by name subcategory has started.");

        Optional<Subcategory> found = repository.findByNameIgnoreCase(name);

        if (found.isEmpty()) {
            LOGGER.info("The subcategory not found. name = {}", name);
        } else {
            LOGGER.info("The subcategory was found. subcategory = {}", found.get());
        }

        return found;
    }
}

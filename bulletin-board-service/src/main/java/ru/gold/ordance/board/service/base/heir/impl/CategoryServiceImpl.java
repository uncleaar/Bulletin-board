package ru.gold.ordance.board.service.base.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.model.entity.domain.Category;
import ru.gold.ordance.board.persistence.repository.heir.CategoryRepository;
import ru.gold.ordance.board.persistence.utils.StorageHelper;
import ru.gold.ordance.board.service.base.heir.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository repository;

    private final StorageHelper helper;

    public CategoryServiceImpl(CategoryRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Category> findAll() {
        LOGGER.info("The search for all categories has started.");

        List<Category> categories = repository.findAll();

        LOGGER.info("Size of list: {}", categories.size());

        return categories;
    }

    @Override
    public @Nullable Optional<Category> findById(@NotNull Long id) {
        LOGGER.info("The search by id category has started.");

        Optional<Category> category = repository.findById(id);

        if (category.isEmpty()) {
            LOGGER.info("The category not found. entityId = {}", id);
        } else {
            LOGGER.info("The category was found. category = {}", category.get());
        }

        return category;
    }

    @Override
    public @Nullable Optional<Category> findByName(@NotNull String name) {
        LOGGER.info("The search by name category has started.");

        Optional<Category> found = repository.findByNameIgnoreCase(name);

        if (found.isEmpty()) {
            LOGGER.info("The category not found. name = {}", name);
        } else {
            LOGGER.info("The category was found. category = {}", found.get());
        }

        return found;
    }

    @Override
    public @NotNull Optional<Category> update(@NotNull Category category) {
        LOGGER.info("Update category has started.");

        boolean exists = helper.exists(category);
        if (!exists && category.getId() != null) {
            LOGGER.info("The category does not exist by the passed id. category = {}", category);

            return Optional.empty();
        }

        Category updatedCategory = repository.saveAndFlush(category);

        if (exists) {
            LOGGER.info("The category was updated. category = {}", updatedCategory);
        } else {
            LOGGER.info("The category was saved. category = {}", updatedCategory);
        }

        return Optional.of(updatedCategory);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete category has started.");

        Optional<Category> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The category was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The category does not exist. entityId = {}", id);
        }
    }
}

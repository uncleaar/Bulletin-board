package ru.gold.ordance.board.core.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gold.ordance.board.core.service.heir.CategoryService;
import ru.gold.ordance.board.core.entity.Category;
import ru.gold.ordance.board.core.persistence.heir.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Autowired
    private CategoryService service;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Category> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.save(createCategoryWithoutSubcategories());

        List<Category> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.save(createCategoryWithoutSubcategories());
        repository.save(createCategoryWithoutSubcategories());

        List<Category> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Category> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Category saved = repository.save(createCategoryWithoutSubcategories());

        Optional<Category> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveCategory() {
        Category created = createCategoryWithoutSubcategories();

        Category saved = service.update(created).get();

        assertEquals(created, saved);
    }

    @Test
    public void update_updateCategory() {
        Long entityId = repository.save(createCategoryWithoutSubcategories()).getId();

        Category newObj = createCategoryWithoutSubcategories();
        newObj.setId(entityId);

        Category updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getName(), updated.getName());
    }

    @Test
    public void deleteById_categoryExists() {
        Category saved = repository.save(createCategoryWithoutSubcategories());

        service.deleteById(saved.getId());

        Optional<Category> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = Strings.EMPTY;

        Optional<Category> found = service.findByName(fakeName);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_found() {
        Category saved = repository.save(createCategoryWithoutSubcategories());

        Optional<Category> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }
}

package ru.gold.ordance.board.core.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.core.service.heir.SubcategoryService;
import ru.gold.ordance.board.core.entity.Category;
import ru.gold.ordance.board.core.entity.Subcategory;
import ru.gold.ordance.board.core.persistence.heir.SubcategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class SubcategoryServiceTest {

    @Autowired
    private SubcategoryService service;

    @Autowired
    private SubcategoryRepository repository;

    @Autowired
    private TestEntityManager manager;

    private Category savedCategory;

    @BeforeEach
    public void setUp() {
        savedCategory = manager.persistAndFlush(createCategoryWithoutSubcategories());
    }

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Subcategory> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.save(createSubcategory(savedCategory));

        List<Subcategory> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.save(createSubcategory(savedCategory));
        repository.save(createSubcategory(savedCategory));

        List<Subcategory> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Subcategory> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Subcategory saved = repository.save(createSubcategory(savedCategory));

        Optional<Subcategory> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveSubcategory_categoryDoesNotExistInStorage() {
        Subcategory created = createSubcategory(999L);

        Optional<Subcategory> saved = service.update(created);

        assertTrue(saved.isEmpty());
    }

    @Test
    public void update_saveSubcategory_categoryExistsInStorage() {
        Subcategory created = createSubcategory(savedCategory);

        Optional<Subcategory> saved = service.update(created);

        assertTrue(saved.isPresent());
        assertEquals(created, saved.get());
    }

    @Test
    public void update_updateSubcategory() {
        Long entityId = repository.save(createSubcategory(savedCategory)).getId();

        Subcategory newObj = createSubcategory(savedCategory);
        newObj.setId(entityId);

        Subcategory updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getName(), updated.getName());
    }

    @Test
    public void deleteById_subcategoryExists() {
        Subcategory saved = repository.save(createSubcategory(savedCategory));

        service.deleteById(saved.getId());

        Optional<Subcategory> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = Strings.EMPTY;

        Optional<Subcategory> found = service.findByName(fakeName);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_found() {
        Subcategory saved = repository.save(createSubcategory(savedCategory));

        Optional<Subcategory> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }
}

package ru.gold.ordance.board.persistence.repository;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.domain.*;
import ru.gold.ordance.board.persistence.repository.bean.SubcategoryRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class SubcategoryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    private Category category;
    private Advertisement advertisement;

    @BeforeEach
    public void setUp() {
        category = createCategory(createSubcategories());
        advertisement = createAdvertisement(createSubcategory());
    }

    @Test
    public void persistCategory_subcategoriesShouldSave() {
        entityManager.persist(category);

        assertNotNull(first(category.getSubcategories()).getId());
    }

    @Test
    public void mergeCategory_subcategoriesShouldSave() {
        Category savedCategory = entityManager.merge(category);

        assertNotNull(first(savedCategory.getSubcategories()).getId());
    }

    @Test
    public void removeCategory_subcategoriesShouldSave() {
        entityManager.persist(category);

        entityManager.remove(category);

        Optional<Subcategory> fromStorage = subcategoryRepository.findById(first(category.getSubcategories()).getId());

        assertTrue(fromStorage.isEmpty());
    }

    @Test
    public void detachCategory_subcategoriesShouldSave() {
        Category savedCategory = entityManager.persist(category);
        entityManager.flush();

        entityManager.detach(savedCategory);
        first(savedCategory.getSubcategories()).setName(Strings.EMPTY);

        Optional<Subcategory> fromStorage = subcategoryRepository.findById(first(category.getSubcategories()).getId());

        assertTrue(fromStorage.isPresent());
        assertNull(fromStorage.get().getName());
    }

    @Test
    public void persistAdvertisement_subcategoryShouldNotBeSaved() {
        entityManager.persist(advertisement);

        assertNull(advertisement.getSubcategory().getId());
    }

    @Test
    public void mergeAdvertisement_subcategoryShouldNotBeMerged() {
        Advertisement savedAdvertisement = entityManager.merge(advertisement);

        assertNull(savedAdvertisement.getSubcategory().getId());
    }

    @Test
    public void removeAdvertisement_subcategoryShouldNotBeRemoved() {
        entityManager.persist(advertisement.getSubcategory());
        entityManager.persist(advertisement);

        entityManager.remove(advertisement);

        Optional<Subcategory> fromStorage = subcategoryRepository.findById(advertisement.getSubcategory().getId());

        assertTrue(fromStorage.isPresent());
    }


    private Subcategory first(Set<Subcategory> subcategories) {
        return subcategories.stream()
                .findFirst()
                .orElse(null);
    }
}

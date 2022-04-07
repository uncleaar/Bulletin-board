package ru.gold.ordance.board.model.entity.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.model.utils.test.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class CategoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistCategory_subcategoriesShouldBePersisted() {
        Category saved = entityManager.persist(
                createCategory(Set.of(createSubcategoryWithoutCategory())));

        assertNotNull(first(saved.getSubcategories()).getId());
    }

    @Test
    public void mergeCategory_subcategoriesShouldBetMerged() {
        Category savedCategory = entityManager.persist(
                createCategory(Set.of(createSubcategoryWithoutCategory())));

        entityManager.flush();
        entityManager.detach(savedCategory);

        first(savedCategory.getSubcategories()).setName(Strings.EMPTY);
        Category mergedCategory = entityManager.merge(savedCategory);

        assertTrue(first(mergedCategory.getSubcategories()).getName().isEmpty());
    }

    @Test
    public void removeCategory_subcategoriesShouldBeRemoved() {
        Category savedCategory = entityManager.persist(
                createCategory(Set.of(createSubcategoryWithoutCategory())));

        entityManager.flush();

        entityManager.remove(savedCategory);

        Subcategory subcategory = entityManager.find(Subcategory.class, first(savedCategory.getSubcategories()).getId());

        assertNull(subcategory);
    }

    private Subcategory first(Set<Subcategory> subcategories) {
        return subcategories.stream()
                .findFirst()
                .orElse(null);
    }
}

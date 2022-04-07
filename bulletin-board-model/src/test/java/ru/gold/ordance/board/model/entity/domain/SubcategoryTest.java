package ru.gold.ordance.board.model.entity.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.model.utils.test.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class SubcategoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistSubcategory_categoryDoesNotPersist() {
        Subcategory saved = entityManager.persist(createSubcategory());

        assertNull(saved.getCategory().getId());
    }

    @Test
    public void mergeSubcategory_categoryDoesNotMerge() {
        Category savedCategory = entityManager.persist(createCategoryWithoutSubcategories());
        Subcategory savedSubcategory = entityManager.persist(createSubcategory(savedCategory));

        entityManager.flush();
        entityManager.detach(savedCategory);
        entityManager.detach(savedSubcategory);

        savedSubcategory.getCategory().setName(Strings.EMPTY);
        Subcategory mergedSubcategory = entityManager.merge(savedSubcategory);

        assertFalse(mergedSubcategory.getCategory().getName().isEmpty());
    }

    @Test
    public void removeSubcategory_categoryDoesNotRemove() {
        Category savedCategory = entityManager.persist(createCategoryWithoutSubcategories());
        Subcategory savedSubcategory = entityManager.persist(createSubcategory(savedCategory));

        entityManager.flush();

        entityManager.remove(savedSubcategory);

        Category category = entityManager.find(Category.class, savedCategory.getId());

        assertNotNull(category);
    }
}


package ru.gold.ordance.board.core.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Region;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
public class LocalityTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistLocality_regionDoesNotPersist() {
        Locality saved = entityManager.persist(createLocality());

        assertNull(saved.getRegion().getId());
    }

    @Test
    public void mergeLocality_regionDoesNotMerge() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));

        entityManager.flush();
        entityManager.detach(savedRegion);
        entityManager.detach(savedLocality);

        savedLocality.getRegion().setName(Strings.EMPTY);
        Locality mergedLocality = entityManager.merge(savedLocality);

        assertFalse(mergedLocality.getRegion().getName().isEmpty());
    }

    @Test
    public void removeLocality_regionDoesNotRemove() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));

        entityManager.flush();

        entityManager.remove(savedLocality);

        Region region = entityManager.find(Region.class, savedRegion.getId());

        assertNotNull(region);
    }
}
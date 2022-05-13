package ru.gold.ordance.board.core.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.core.entity.LnkLocalityStreet;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Region;
import ru.gold.ordance.board.core.entity.Street;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class LnkLocalityStreetTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistLnkLocalityStreet_localityAndStreetDoesNotPersist() {
        LnkLocalityStreet saved = entityManager.persist(createLnkLocalityStreet());

        assertNull(saved.getLocality().getId());
        assertNull(saved.getStreet().getId());
    }

    @Test
    public void mergeLnkLocalityStreet_localityAndStreetDoesNotMerge() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));
        Street savedStreet = entityManager.persist(createStreet());
        LnkLocalityStreet savedLnkLocalityStreet = entityManager.persist(createLnkLocalityStreet(savedLocality, savedStreet));

        entityManager.flush();
        entityManager.detach(savedRegion);
        entityManager.detach(savedLocality);
        entityManager.detach(savedStreet);
        entityManager.detach(savedLnkLocalityStreet);

        savedLnkLocalityStreet.getLocality().setName(Strings.EMPTY);
        savedLnkLocalityStreet.getStreet().setName(Strings.EMPTY);
        LnkLocalityStreet mergedLnkLocalityStreet = entityManager.merge(savedLnkLocalityStreet);

        assertFalse(mergedLnkLocalityStreet.getLocality().getName().isEmpty());
        assertFalse(mergedLnkLocalityStreet.getStreet().getName().isEmpty());
    }

    @Test
    public void removeLnkLocalityStreet_localityAndStreetDoesNotRemove() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));
        Street savedStreet = entityManager.persist(createStreet());
        LnkLocalityStreet savedLnkLocalityStreet = entityManager.persist(createLnkLocalityStreet(savedLocality, savedStreet));

        entityManager.flush();

        entityManager.remove(savedLnkLocalityStreet);

        Locality locality = entityManager.find(Locality.class, savedLocality.getId());
        Street street = entityManager.find(Street.class, savedStreet.getId());

        assertNotNull(locality);
        assertNotNull(street);
    }
}

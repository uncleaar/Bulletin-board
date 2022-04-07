package ru.gold.ordance.board.model.entity.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.model.utils.test.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class AddressTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistAddress_localityAndStreetDoesNotPersist() {
        Address saved = entityManager.persist(createAddress());

        assertNull(saved.getLocality().getId());
        assertNull(saved.getStreet().getId());
    }

    @Test
    public void mergeAddress_localityAndStreetDoesNotMerge() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));
        Street savedStreet = entityManager.persist(createStreet());
        Address savedAddress = entityManager.persist(createAddress(savedLocality, savedStreet));

        entityManager.flush();
        entityManager.detach(savedRegion);
        entityManager.detach(savedLocality);
        entityManager.detach(savedStreet);
        entityManager.detach(savedAddress);

        savedAddress.getLocality().setName(Strings.EMPTY);
        savedAddress.getStreet().setName(Strings.EMPTY);
        Address mergedAddress = entityManager.merge(savedAddress);

        assertFalse(mergedAddress.getLocality().getName().isEmpty());
        assertFalse(mergedAddress.getStreet().getName().isEmpty());
    }

    @Test
    public void removeAddress_localityAndStreetDoesNotRemove() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));
        Street savedStreet = entityManager.persist(createStreet());
        Address savedAddress = entityManager.persist(createAddress(savedLocality, savedStreet));

        entityManager.flush();

        entityManager.remove(savedAddress);

        Locality locality = entityManager.find(Locality.class, savedLocality.getId());
        Street street = entityManager.find(Street.class, savedStreet.getId());

        assertNotNull(locality);
        assertNotNull(street);
    }
}

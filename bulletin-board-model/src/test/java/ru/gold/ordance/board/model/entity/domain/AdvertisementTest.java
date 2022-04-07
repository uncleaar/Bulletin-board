package ru.gold.ordance.board.model.entity.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.model.entity.authorization.Client;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.model.utils.test.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class AdvertisementTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void persistAdvertisement_clientDoesNotPersist() {
        Advertisement saved = entityManager.persist(createAdvertisementWithOnlyClient(createClient()));

        assertNull(saved.getClient().getId());
    }

    @Test
    public void mergeAdvertisement_clientDoesNotMerge() {
        Client savedClient = entityManager.persist(createClient());
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlyClient(savedClient));

        entityManager.flush();
        entityManager.detach(savedClient);
        entityManager.detach(savedAdvertisement);

        savedAdvertisement.getClient().setName(Strings.EMPTY);
        Advertisement mergedAdvertisement = entityManager.merge(savedAdvertisement);

        assertFalse(mergedAdvertisement.getClient().getName().isEmpty());
    }

    @Test
    public void removeAdvertisement_clientDoesNotRemove() {
        Client savedClient = entityManager.persist(createClient());
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlyClient(savedClient));

        entityManager.flush();

        entityManager.remove(savedAdvertisement);

        Client client = entityManager.find(Client.class, savedClient.getId());

        assertNotNull(client);
    }

    @Test
    public void persistAdvertisement_subcategoryDoesNotPersist() {
        Advertisement saved = entityManager.persist(createAdvertisementWithOnlySubcategory(createSubcategory()));

        assertNull(saved.getSubcategory().getId());
    }

    @Test
    public void mergeAdvertisement_subcategoryDoesNotMerge() {
        Subcategory savedSubcategory = entityManager.persist(createSubcategoryWithoutCategory());
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlySubcategory(savedSubcategory));

        entityManager.flush();
        entityManager.detach(savedSubcategory);
        entityManager.detach(savedAdvertisement);

        savedAdvertisement.getSubcategory().setName(Strings.EMPTY);
        Advertisement mergedAdvertisement = entityManager.merge(savedAdvertisement);

        assertFalse(mergedAdvertisement.getSubcategory().getName().isEmpty());
    }

    @Test
    public void removeAdvertisement_subcategoryDoesNotRemove() {
        Subcategory savedSubcategory = entityManager.persist(createSubcategoryWithoutCategory());
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlySubcategory(savedSubcategory));

        entityManager.flush();

        entityManager.remove(savedAdvertisement);

        Subcategory subcategory = entityManager.find(Subcategory.class, savedSubcategory.getId());

        assertNotNull(subcategory);
    }

    @Test
    public void persistAdvertisement_localityDoesNotPersist() {
        Advertisement saved = entityManager.persist(createAdvertisementWithOnlyLocality(createLocality()));

        assertNull(saved.getLocality().getId());
    }

    @Test
    public void mergeAdvertisement_localityDoesNotMerge() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlyLocality(savedLocality));

        entityManager.flush();
        entityManager.detach(savedRegion);
        entityManager.detach(savedLocality);
        entityManager.detach(savedAdvertisement);

        savedAdvertisement.getLocality().setName(Strings.EMPTY);
        Advertisement mergedAdvertisement = entityManager.merge(savedAdvertisement);

        assertFalse(mergedAdvertisement.getLocality().getName().isEmpty());
    }

    @Test
    public void removeAdvertisement_localityDoesNotRemove() {
        Region savedRegion = entityManager.persist(createRegion());
        Locality savedLocality = entityManager.persist(createLocality(savedRegion));
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlyLocality(savedLocality));

        entityManager.flush();

        entityManager.remove(savedAdvertisement);

        Locality locality = entityManager.find(Locality.class, savedLocality.getId());

        assertNotNull(locality);
    }

    @Test
    public void persistAdvertisement_streetDoesNotPersist() {
        Advertisement saved = entityManager.persist(createAdvertisementWithOnlyStreet(createStreet()));

        assertNull(saved.getStreet().getId());
    }

    @Test
    public void mergeAdvertisement_streetDoesNotMerge() {
        Street savedStreet = entityManager.persist(createStreet());
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlyStreet(savedStreet));

        entityManager.flush();
        entityManager.detach(savedStreet);
        entityManager.detach(savedAdvertisement);

        savedAdvertisement.getStreet().setName(Strings.EMPTY);
        Advertisement mergedAdvertisement = entityManager.merge(savedAdvertisement);

        assertFalse(mergedAdvertisement.getStreet().getName().isEmpty());
    }

    @Test
    public void removeAdvertisement_streetDoesNotRemove() {
        Street savedStreet = entityManager.persist(createStreet());
        Advertisement savedAdvertisement = entityManager.persist(createAdvertisementWithOnlyStreet(savedStreet));

        entityManager.flush();

        entityManager.remove(savedAdvertisement);

        Street street = entityManager.find(Street.class, savedStreet.getId());

        assertNotNull(street);
    }
}

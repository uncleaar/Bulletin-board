package ru.gold.ordance.board.core.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.core.entity.*;
import ru.gold.ordance.board.core.service.heir.AdvertisementService;
import ru.gold.ordance.board.core.persistence.heir.AdvertisementRepository;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class AdvertisementServiceTest {

    @Autowired
    private AdvertisementService service;

    @Autowired
    private AdvertisementRepository repository;

    @Autowired
    private TestEntityManager manager;

    private Client savedClient;

    private Subcategory savedSubcategory;

    private Locality savedLocality;

    private Street savedStreet;

    @BeforeEach
    public void setUp() {
        Region savedRegion = manager.persistAndFlush(createRegion());
        Category savedCategory = manager.persistAndFlush(createCategoryWithoutSubcategories());

        savedClient = manager.persistAndFlush(createClient());
        savedSubcategory = manager.persistAndFlush(createSubcategory(savedCategory));
        savedLocality = manager.persistAndFlush(createLocality(savedRegion));
        savedStreet = manager.persistAndFlush(createStreet());
    }

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Advertisement> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));

        List<Advertisement> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));
        repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));

        List<Advertisement> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Advertisement> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Advertisement saved =
                repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));

        Optional<Advertisement> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveAdvertisement_clientOrSubcategoryOrStreetOrLocalityDoesNotExistInStorage() {
        Advertisement created = createAdvertisement(999L);

        Optional<Advertisement> saved = service.update(created);

        assertTrue(saved.isEmpty());
    }

    @Test
    public void update_saveAdvertisement_clientOrSubcategoryOrStreetOrLocalityExistsInStorage() {
        Advertisement created = createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet);

        Optional<Advertisement> saved = service.update(created);

        assertTrue(saved.isPresent());
        assertEquals(created, saved.get());
    }

    @Test
    public void update_updateAdvertisement() {
        Long entityId =
                repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet)).getId();

        Advertisement newObj = createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet);
        newObj.setId(entityId);

        Advertisement updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getClient().getId(), updated.getClient().getId());
        assertEquals(newObj.getSubcategory().getId(), updated.getSubcategory().getId());
        assertEquals(newObj.getLocality().getId(), updated.getLocality().getId());
        assertEquals(newObj.getStreet().getId(), updated.getStreet().getId());
        assertEquals(newObj.getHouseNumber(), updated.getHouseNumber());
        assertEquals(newObj.getCreateDate(), updated.getCreateDate());
        assertEquals(newObj.getDescription(), updated.getDescription());
        assertEquals(newObj.getName(), updated.getName());
    }

    @Test
    public void deleteById_advertisementExists() {
        Advertisement saved =
                repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));

        service.deleteById(saved.getId());

        Optional<Advertisement> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findAllByName_notFound() {
        String fakeName = Strings.EMPTY;
        int noOneHasBeenFound = 0;

        List<Advertisement> found = service.findAllByName(fakeName);

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAllByName_foundOne() {
        int foundOne = 1;
        Advertisement saved =
                repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));
        repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));

        List<Advertisement> found = service.findAllByName(saved.getName());

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAllByName_foundALot() {
        int foundALot = 2;
        Advertisement saved =
                repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet));
        repository.saveAndFlush(createAdvertisement(savedClient, savedSubcategory, savedLocality, savedStreet, saved.getName()));

        List<Advertisement> found = service.findAllByName(saved.getName());

        assertEquals(foundALot, found.size());
    }
}

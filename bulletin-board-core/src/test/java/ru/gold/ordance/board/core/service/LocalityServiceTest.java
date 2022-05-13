package ru.gold.ordance.board.core.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.core.service.heir.LocalityService;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.entity.Region;
import ru.gold.ordance.board.core.persistence.heir.LocalityRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class LocalityServiceTest {

    @Autowired
    private LocalityService service;

    @Autowired
    private LocalityRepository localityRepository;

    @Autowired
    private TestEntityManager manager;

    private Region savedRegion;

    @BeforeEach
    public void setUp() {
        savedRegion = manager.persistAndFlush(createRegion());
    }

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Locality> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        localityRepository.saveAndFlush(createLocality(savedRegion));

        List<Locality> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        localityRepository.saveAndFlush(createLocality(savedRegion));
        localityRepository.saveAndFlush(createLocality(savedRegion));

        List<Locality> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Locality> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Locality saved = localityRepository.saveAndFlush(createLocality(savedRegion));

        Optional<Locality> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveLocalityWithRegion_regionDoesNotExistInStorage() {
        Locality created = createLocality(999L);

        Optional<Locality> saved = service.update(created);

        assertTrue(saved.isEmpty());
    }

    @Test
    public void update_saveLocalityWithRegion_regionExistsInStorage() {
        Locality created = createLocality(savedRegion);

        Optional<Locality> savedLocality = service.update(created);

        assertTrue(savedLocality.isPresent());
        assertEquals(created, savedLocality.get());
    }

    @Test
    public void update_updateLocality() {
        Long entityId = localityRepository.save(createLocality(savedRegion)).getId();

        Locality newObj = createLocality(savedRegion);
        newObj.setId(entityId);

        Locality updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getName(), updated.getName());
    }

    @Test
    public void deleteById_localityExists() {
        Locality savedLocality = localityRepository.save(createLocality(savedRegion));

        service.deleteById(savedLocality.getId());

        Optional<Locality> found = localityRepository.findById(savedLocality.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = Strings.EMPTY;

        Optional<Locality> empty = service.findByName(fakeName);

        assertTrue(empty.isEmpty());
    }

    @Test
    public void findByName_found() {
        Locality saved = localityRepository.save(createLocality(savedRegion));

        Optional<Locality> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }
}

package ru.gold.ordance.board.service.base.heir;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.model.entity.domain.LnkLocalityStreet;
import ru.gold.ordance.board.model.entity.domain.Locality;
import ru.gold.ordance.board.model.entity.domain.Region;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.persistence.repository.heir.LnkLocalityStreetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.model.entity.utils.test.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class LnkLocalityStreetServiceTest {

    @Autowired
    private LnkLocalityStreetService service;

    @Autowired
    private LnkLocalityStreetRepository repository;

    @Autowired
    private TestEntityManager manager;

    private Locality savedLocality;

    private Street savedStreet;

    @BeforeEach
    public void setUp() {
        Region savedRegion = manager.persistAndFlush(createRegion());
        savedLocality = manager.persistAndFlush(createLocality(savedRegion));
        savedStreet = manager.persistAndFlush(createStreet());
    }

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<LnkLocalityStreet> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.saveAndFlush(createLnkLocalityStreet(savedLocality, savedStreet));

        List<LnkLocalityStreet> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        Street otherSavedStreet = manager.persistAndFlush(createStreet());

        repository.save(createLnkLocalityStreet(savedLocality, savedStreet));
        repository.save(createLnkLocalityStreet(savedLocality, otherSavedStreet));

        List<LnkLocalityStreet> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<LnkLocalityStreet> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        LnkLocalityStreet saved = repository.save(createLnkLocalityStreet(savedLocality, savedStreet));

        Optional<LnkLocalityStreet> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveLnkLocalityStreet_streetOrLocalityDoesNotExistInStorage() {
        LnkLocalityStreet created = createLnkLocalityStreet(999L);

        Optional<LnkLocalityStreet> saved = service.update(created);

        assertTrue(saved.isEmpty());
    }

    @Test
    public void update_saveLnkLocalityStreet_streetAndLocalityExistsInStorage() {
        LnkLocalityStreet created = createLnkLocalityStreet(savedLocality, savedStreet);

        Optional<LnkLocalityStreet> saved = service.update(created);

        assertTrue(saved.isPresent());
        assertEquals(created, saved.get());
    }

    @Test
    public void update_updateLnkLocalityStreet() {
        Long entityId = repository.save(createLnkLocalityStreet(savedLocality, savedStreet)).getId();

        LnkLocalityStreet newObj =
                createLnkLocalityStreet(
                        manager.persistAndFlush(createLocality(manager.persistAndFlush(createRegion()))),
                        manager.persistAndFlush(createStreet()));
        newObj.setId(entityId);

        LnkLocalityStreet updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getLocality().getId(), updated.getLocality().getId());
        assertEquals(newObj.getStreet().getId(), updated.getStreet().getId());
    }

    @Test
    public void deleteById_clientExists() {
        LnkLocalityStreet saved = repository.save(createLnkLocalityStreet(savedLocality, savedStreet));

        service.deleteById(saved.getId());

        Optional<LnkLocalityStreet> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByLocalityAndStreet_notFound() {
        long fakeId = 999L;

        Optional<LnkLocalityStreet> found = service.findByLocalityAndStreet(createLocality(fakeId), createStreet(fakeId));

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByLocalityAndStreet_found() {
        repository.save(createLnkLocalityStreet(savedLocality, savedStreet));

        Optional<LnkLocalityStreet> found = service.findByLocalityAndStreet(savedLocality, savedStreet);

        assertTrue(found.isPresent());
    }

    @Test
    public void findByLocality_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;
        long fakeId = 999L;

        List<LnkLocalityStreet> found = service.findByLocality(createLocality(fakeId));

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findByLocality_foundOne() {
        int foundOne = 1;
        repository.save(createLnkLocalityStreet(savedLocality, savedStreet));

        List<LnkLocalityStreet> found = service.findByLocality(savedLocality);

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findByLocality_foundALot() {
        int foundALot = 2;
        Street otherSavedStreet = manager.persistAndFlush(createStreet());

        repository.save(createLnkLocalityStreet(savedLocality, savedStreet));
        repository.save(createLnkLocalityStreet(savedLocality, otherSavedStreet));

        List<LnkLocalityStreet> found = service.findByLocality(savedLocality);

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findByStreet_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;
        long fakeId = 999L;

        List<LnkLocalityStreet> found = service.findByStreet(createStreet(fakeId));

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findByStreet_foundOne() {
        int foundOne = 1;
        repository.save(createLnkLocalityStreet(savedLocality, savedStreet));

        List<LnkLocalityStreet> found = service.findByStreet(savedStreet);

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findByStreet_foundALot() {
        int foundALot = 2;
        Region savedRegion = manager.persistAndFlush(createRegion());
        Locality otherSavedLocality = manager.persistAndFlush(createLocality(savedRegion));

        repository.save(createLnkLocalityStreet(savedLocality, savedStreet));
        repository.save(createLnkLocalityStreet(otherSavedLocality, savedStreet));

        List<LnkLocalityStreet> found = service.findByStreet(savedStreet);

        assertEquals(foundALot, found.size());
    }
}

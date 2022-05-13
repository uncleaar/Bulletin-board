package ru.gold.ordance.board.core.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.gold.ordance.board.core.service.heir.StreetService;
import ru.gold.ordance.board.core.entity.Street;
import ru.gold.ordance.board.core.persistence.heir.StreetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.core.utils.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class StreetServiceTest {

    @Autowired
    private StreetService service;

    @Autowired
    private StreetRepository repository;

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Street> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.save(createStreet());

        List<Street> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.save(createStreet());
        repository.save(createStreet());

        List<Street> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Street> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Street saved = repository.save(createStreet());

        Optional<Street> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveStreet() {
        Street created = createStreet();

        Street saved = service.update(created).get();

        assertEquals(created, saved);
    }

    @Test
    public void update_updateStreet() {
        Long entityId = repository.save(createStreet()).getId();

        Street newObj = createStreet();
        newObj.setId(entityId);

        Street updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getName(), updated.getName());
    }

    @Test
    public void deleteById_streetExists() {
        Street saved = repository.save(createStreet());

        service.deleteById(saved.getId());

        Optional<Street> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = Strings.EMPTY;

        Optional<Street> found = service.findByName(fakeName);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_found() {
        Street saved = repository.save(createStreet());

        Optional<Street> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }
}

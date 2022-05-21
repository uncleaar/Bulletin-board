package ru.gold.ordance.board.core.service;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gold.ordance.board.core.service.heir.RegionService;
import ru.gold.ordance.board.core.entity.Region;
import ru.gold.ordance.board.core.persistence.heir.RegionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.core.utils.EntityGenerator.createRegion;

@DataJpaTest(showSql = false)
@ActiveProfiles("test")
public class RegionServiceTest {

    @Autowired
    private RegionService service;

    @Autowired
    private RegionRepository repository;

    @Test
    public void findAll_noOneHasBeenFound() {
        int noOneHasBeenFound = 0;

        List<Region> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.save(createRegion());

        List<Region> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.save(createRegion());
        repository.save(createRegion());

        List<Region> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Region> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Region created = repository.save(createRegion());

        Optional<Region> found = service.findById(created.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveRegion() {
        Region created = createRegion();

        Region saved = service.update(created).get();

        assertEquals(created, saved);
    }

    @Test
    public void update_updateRegion() {
        Long entityId = repository.save(createRegion()).getId();

        Region newObj = createRegion();
        newObj.setId(entityId);

        Region updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getName(), updated.getName());
    }

    @Test
    public void deleteById_regionExists() {
        Region saved = repository.save(createRegion());

        service.deleteById(saved.getId());

        Optional<Region> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_notFound() {
        String fakeName = Strings.EMPTY;

        Optional<Region> found = service.findByName(fakeName);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findByName_found() {
        Region saved = repository.save(createRegion());

        Optional<Region> found = service.findByName(saved.getName());

        assertTrue(found.isPresent());
        assertEquals(saved, found.get());
    }
}

package ru.gold.ordance.board.service.base.heir;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.gold.ordance.board.model.entity.domain.Address;
import ru.gold.ordance.board.model.entity.domain.Locality;
import ru.gold.ordance.board.model.entity.domain.Region;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.persistence.repository.heir.AddressRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.model.entity.utils.test.EntityGenerator.*;

@DataJpaTest(showSql = false)
public class AddressServiceTest {

    @Autowired
    private AddressService service;

    @Autowired
    private AddressRepository repository;

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

        List<Address> found = service.findAll();

        assertEquals(noOneHasBeenFound, found.size());
    }

    @Test
    public void findAll_foundOne() {
        int foundOne = 1;
        repository.saveAndFlush(createAddress(savedLocality, savedStreet));

        List<Address> found = service.findAll();

        assertEquals(foundOne, found.size());
    }

    @Test
    public void findAll_foundALot() {
        int foundALot = 2;
        repository.saveAndFlush(createAddress(savedLocality, savedStreet));
        repository.saveAndFlush(createAddress(savedLocality, savedStreet));

        List<Address> found = service.findAll();

        assertEquals(foundALot, found.size());
    }

    @Test
    public void findById_notFound() {
        long fakeId = 0L;

        Optional<Address> found = service.findById(fakeId);

        assertTrue(found.isEmpty());
    }

    @Test
    public void findById_found() {
        Address saved = repository.saveAndFlush(createAddress(savedLocality, savedStreet));

        Optional<Address> found = service.findById(saved.getId());

        assertTrue(found.isPresent());
    }

    @Test
    public void update_saveAddress_streetOrLocalityDoesNotExistInStorage() {
        Address created = createAddress(999L);

        Optional<Address> saved = service.update(created);

        assertTrue(saved.isEmpty());
    }

    @Test
    public void update_saveAddress_streetAndLocalityExistsInStorage() {
        Address created = createAddress(savedLocality, savedStreet);

        Optional<Address> saved = service.update(created);

        assertTrue(saved.isPresent());
        assertEquals(created, saved.get());
    }

    @Test
    public void update_updateAddress() {
        Long entityId = repository.save(createAddress(savedLocality, savedStreet)).getId();

        Address newObj = createAddress(savedLocality, savedStreet);
        newObj.setId(entityId);

        Address updated = service.update(newObj).get();

        assertEquals(newObj.getId(), updated.getId());
        assertEquals(newObj.getLocality().getId(), updated.getLocality().getId());
        assertEquals(newObj.getStreet().getId(), updated.getStreet().getId());
        assertEquals(newObj.getHouseNumber(), updated.getHouseNumber());
    }

    @Test
    public void deleteById_addressExists() {
        Address saved = repository.save(createAddress(savedLocality, savedStreet));

        service.deleteById(saved.getId());

        Optional<Address> found = repository.findById(saved.getId());

        assertTrue(found.isEmpty());
    }
}

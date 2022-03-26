package ru.gold.ordance.board.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.domain.Address;
import ru.gold.ordance.board.model.domain.Region;
import ru.gold.ordance.board.persistence.repository.heir.RegionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class RegionRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegionRepository regionRepository;

    private Address address;

    @BeforeEach
    public void setUp() {
        address = createAddress(createRegion());
    }

    @Test
    public void persistAddress_regionShouldNotBeSaved() {
        entityManager.persist(address);

        assertNull(address.getRegion().getId());
    }

    @Test
    public void mergeAddress_regionShouldNotBeMerged() {
        Address savedAddress = entityManager.merge(address);

        assertNull(savedAddress.getRegion().getId());
    }

    @Test
    public void removeAddress_regionShouldNotBeRemoved() {
        entityManager.persist(address.getRegion());
        entityManager.persist(address);

        entityManager.remove(address);

        Optional<Region> fromStorage = regionRepository.findById(address.getRegion().getId());

        assertTrue(fromStorage.isPresent());
    }
}

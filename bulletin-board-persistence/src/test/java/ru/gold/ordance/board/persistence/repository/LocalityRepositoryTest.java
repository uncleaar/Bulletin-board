package ru.gold.ordance.board.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.domain.Address;
import ru.gold.ordance.board.model.domain.Locality;
import ru.gold.ordance.board.persistence.repository.heir.LocalityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class LocalityRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocalityRepository localityRepository;

    private Address address;

    @BeforeEach
    public void setUp() {
        address = createAddress(createLocality());
    }

    @Test
    public void persistAddress_localityShouldNotBeSaved() {
        entityManager.persist(address);

        assertNull(address.getLocality().getId());
    }

    @Test
    public void mergeAddress_localityShouldNotBeMerged() {
        Address savedAddress = entityManager.merge(address);

        assertNull(savedAddress.getLocality().getId());
    }

    @Test
    public void removeAddress_localityShouldNotBeRemoved() {
        entityManager.persist(address.getLocality());
        entityManager.persist(address);

        entityManager.remove(address);

        Optional<Locality> fromStorage = localityRepository.findById(address.getLocality().getId());

        assertTrue(fromStorage.isPresent());
    }
}

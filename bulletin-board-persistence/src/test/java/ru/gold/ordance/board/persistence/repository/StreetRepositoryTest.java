package ru.gold.ordance.board.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.domain.Address;
import ru.gold.ordance.board.model.domain.Street;
import ru.gold.ordance.board.persistence.repository.heir.StreetRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class StreetRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StreetRepository streetRepository;

    private Address address;

    @BeforeEach
    public void setUp() {
        address = createAddress(createStreet());
    }

    @Test
    public void persistAddress_streetShouldNotBeSaved() {
        entityManager.persist(address);

        assertNull(address.getStreet().getId());
    }

    @Test
    public void mergeAddress_streetShouldNotBeMerged() {
        Address savedAddress = entityManager.merge(address);

        assertNull(savedAddress.getStreet().getId());
    }

    @Test
    public void removeAddress_streetShouldNotBeRemoved() {
        entityManager.persist(address.getStreet());
        entityManager.persist(address);

        entityManager.remove(address);

        Optional<Street> fromStorage = streetRepository.findById(address.getStreet().getId());

        assertTrue(fromStorage.isPresent());
    }
}

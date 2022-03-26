package ru.gold.ordance.board.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.domain.Address;
import ru.gold.ordance.board.model.domain.Advertisement;
import ru.gold.ordance.board.persistence.repository.heir.AddressRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class AddressRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    private Advertisement advertisement;

    @BeforeEach
    public void setUp() {
        advertisement = createAdvertisement(createAddress());
    }

    @Test
    public void persistAdvertisement_addressShouldNotBeSaved() {
        entityManager.persist(advertisement);

        assertNull(advertisement.getAddress().getId());
    }

    @Test
    public void mergeAdvertisement_addressShouldNotBeMerged() {
        Advertisement savedAdvertisement = entityManager.merge(advertisement);

        assertNull(savedAdvertisement.getAddress().getId());
    }

    @Test
    public void removeAdvertisement_addressShouldNotBeRemoved() {
        entityManager.persist(advertisement.getAddress());
        entityManager.persist(advertisement);

        entityManager.remove(advertisement);

        Optional<Address> fromStorage = addressRepository.findById(advertisement.getAddress().getId());

        assertTrue(fromStorage.isPresent());
    }
}
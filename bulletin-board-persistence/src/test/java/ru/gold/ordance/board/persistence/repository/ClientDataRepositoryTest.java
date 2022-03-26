package ru.gold.ordance.board.persistence.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.authorization.ClientData;
import ru.gold.ordance.board.model.domain.Advertisement;
import ru.gold.ordance.board.persistence.repository.heir.ClientDataRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class ClientDataRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientDataRepository clientDataRepository;

    private Advertisement advertisement;

    @BeforeEach
    public void setUp() {
        advertisement = createAdvertisement(createClientData(createClient()));
    }

    @Test
    public void persistAdvertisement_clientDataShouldNotBeSaved() {
        entityManager.persist(advertisement);

        assertNull(advertisement.getClientData().getId());
    }

    @Test
    public void mergeAdvertisement_clientDataShouldNotBeMerged() {
        Advertisement savedAdvertisement = entityManager.merge(advertisement);

        assertNull(savedAdvertisement.getClientData().getId());
    }

    @Test
    public void removeAdvertisement_clientDataShouldNotBeRemoved() {
        entityManager.persist(advertisement.getClientData());
        entityManager.persist(advertisement);

        entityManager.remove(advertisement);

        Optional<ClientData> fromStorage = clientDataRepository.findById(advertisement.getClientData().getId());

        assertTrue(fromStorage.isPresent());
    }
}

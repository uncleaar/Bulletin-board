package ru.gold.ordance.board.persistence.repository;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gold.ordance.board.model.authorization.Client;
import ru.gold.ordance.board.model.authorization.ClientData;
import ru.gold.ordance.board.persistence.repository.bean.ClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.gold.ordance.board.common.test.EntityGenerator.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
public class ClientRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    private ClientData clientData;

    @BeforeEach
    public void setUp() {
        clientData = createClientData(createClient());
    }

    @Test
    public void removeClientData_clientShouldRemove() {
        entityManager.persist(clientData);

        entityManager.remove(clientData);

        Optional<Client> fromStorage = clientRepository.findById(clientData.getClient().getId());

        assertTrue(fromStorage.isEmpty());
    }

    @Test
    public void detachClientData_clientShouldDetach() {
        ClientData savedClientData = entityManager.persist(clientData);
        entityManager.flush();

        entityManager.detach(savedClientData);
        savedClientData.getClient().setPassword(Strings.EMPTY);

        Optional<Client> fromStorage = clientRepository.findById(clientData.getClient().getId());

        assertTrue(fromStorage.isPresent());
        assertNull(fromStorage.get().getPassword());
    }
}

package ru.gold.ordance.board.service.base.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.model.entity.authorization.Client;
import ru.gold.ordance.board.persistence.repository.heir.ClientRepository;
import ru.gold.ordance.board.persistence.utils.StorageHelper;
import ru.gold.ordance.board.service.base.heir.ClientService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClientServiceImpl implements ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final ClientRepository repository;

    private final StorageHelper helper;

    private final PasswordEncoder passwordEncoder;

    public ClientServiceImpl(ClientRepository repository,
                             StorageHelper helper,
                             PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.helper = helper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Client> findAll() {
        LOGGER.info("The search for all clients has started.");

        List<Client> clients = repository.findAll();

        LOGGER.info("Size of list: {}", clients.size());

        return clients;
    }

    @Override
    public @Nullable Optional<Client> findById(@NotNull Long id) {
        LOGGER.info("The search by id client has started.");

        Optional<Client> client = repository.findById(id);

        if (client.isEmpty()) {
            LOGGER.info("The client not found. entityId = {}", id);
        } else {
            LOGGER.info("The client was found. client = {}", client.get());
        }

        return client;
    }

    @Override
    public @NotNull Optional<Client> update(@NotNull Client client) {
        LOGGER.info("Update client has started.");

        Client updatedClient;
        boolean exists = helper.exists(client);

        if (exists) {
            if (!passwordEncoder.matches(client.getPassword(), repository.findById(client.getId()).get().getPassword())) {
                client.setPassword(passwordEncoder.encode(client.getPassword()));
            }
        } else {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
        }

        updatedClient = repository.saveAndFlush(client);

        if (exists) {
            LOGGER.info("The client was updated. client = {}", updatedClient);
        } else {
            LOGGER.info("The client was saved. client = {}", updatedClient);
        }

        return Optional.of(updatedClient);
    }

    @Override
    public void delete(@NotNull Client client) {
        deleteById(client.getId());
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete region has started.");

        Optional<Client> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The region was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The region does not exist. entityId = {}", id);
        }
    }

    @Override
    public List<Client> findAllByName(@NotNull String name) {
        LOGGER.info("The search by name clients has started.");

        List<Client> found = repository.findByNameIgnoreCaseContaining(name);

        LOGGER.info("Size of list: {}", found.size());

        return found;
    }
}

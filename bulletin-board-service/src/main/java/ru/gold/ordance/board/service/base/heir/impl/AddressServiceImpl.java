package ru.gold.ordance.board.service.base.heir.impl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.model.entity.domain.Address;
import ru.gold.ordance.board.persistence.repository.heir.AddressRepository;
import ru.gold.ordance.board.persistence.utils.StorageHelper;
import ru.gold.ordance.board.service.base.heir.AddressService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AddressServiceImpl implements AddressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository repository;

    private final StorageHelper helper;

    public AddressServiceImpl(AddressRepository repository, StorageHelper helper) {
        this.repository = repository;
        this.helper = helper;
    }

    @Override
    public List<Address> findAll() {
        LOGGER.info("The search for all addresses has started.");

        List<Address> addresses = repository.findAll();

        LOGGER.info("Size of list: {}", addresses.size());

        return addresses;
    }

    @Override
    public @Nullable Optional<Address> findById(@NotNull Long id) {
        LOGGER.info("The search by id address has started.");

        Optional<Address> address = repository.findById(id);

        if (address.isEmpty()) {
            LOGGER.info("The address not found. entityId = {}", id);
        } else {
            LOGGER.info("The address was found. address = {}", address.get());
        }

        return address;
    }

    @Override
    public @Nullable Optional<Address> update(@NotNull Address address) {
        LOGGER.info("Update address has started.");

        boolean exists = helper.exists(address);

        if (!helper.existsExternal(address)) {
            LOGGER.info("Address " + (exists ? "save" : "update") + " is ignored. address = {}", address);

            return Optional.empty();
        }

        if (!exists && address.getId() != null) {
            LOGGER.info("The address does not exist by the passed id. address = {}", address);

            return Optional.empty();
        }

        Address updatedAddress = repository.saveAndFlush(address);

        if (exists) {
            LOGGER.info("The address was updated. address = {}", updatedAddress);
        } else {
            LOGGER.info("The address was saved. address = {}", updatedAddress);
        }

        return Optional.of(updatedAddress);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete address has started.");

        Optional<Address> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The address was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The address does not exist. entityId = {}", id);
        }
    }
}

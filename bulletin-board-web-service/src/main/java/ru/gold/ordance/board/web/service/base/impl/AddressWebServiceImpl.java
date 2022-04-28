package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.address.*;
import ru.gold.ordance.board.model.entity.domain.Address;
import ru.gold.ordance.board.service.base.heir.AddressService;
import ru.gold.ordance.board.web.service.base.AddressWebService;
import ru.gold.ordance.board.web.service.mapper.AddressMapper;
import ru.gold.ordance.board.web.service.mapper.impl.AddressMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressWebServiceImpl implements AddressWebService {
    private final AddressService service;

    private final AddressMapper mapper = new AddressMapperImpl();

    public AddressWebServiceImpl(AddressService service) {
        this.service = service;
    }

    @Override
    public AddressGetRs findAll() {
        List<Address> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebAddress> webAddresses = found.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return AddressGetRs.success(webAddresses);
        } else {
            return AddressGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public AddressGetRs findById(AddressGetByIdRq rq) {
        Optional<Address> found = service.findById(rq.getEntityId());

        if (found.isPresent()) {
            WebAddress webAddress = mapper.fromEntity(found.get());

            return AddressGetRs.success(Collections.singletonList(webAddress));
        } else {
            return AddressGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public AddressUpdateRs update(AddressUpdateRq rq) {
        Optional<Address> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return AddressUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public AddressDeleteByIdRs deleteById(AddressDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return AddressDeleteByIdRs.success();
    }
}

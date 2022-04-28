package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.street.*;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.service.base.heir.StreetService;
import ru.gold.ordance.board.web.service.base.StreetWebService;
import ru.gold.ordance.board.web.service.mapper.StreetMapper;
import ru.gold.ordance.board.web.service.mapper.impl.StreetMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StreetWebServiceImpl implements StreetWebService {
    private final StreetService service;

    private final StreetMapper mapper = new StreetMapperImpl();

    public StreetWebServiceImpl(StreetService service) {
        this.service = service;
    }

    @Override
    public StreetGetRs findAll() {
        List<Street> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebStreet> webStreets = found.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return StreetGetRs.success(webStreets);
        } else {
            return StreetGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public StreetGetRs findById(StreetGetByIdRq rq) {
        Optional<Street> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public StreetGetRs findByName(StreetGetByNameRq rq) {
        Optional<Street> found = service.findByName(rq.getName());

        return searchResult(found);
    }

    @Override
    public StreetUpdateRs update(StreetUpdateRq rq) {
        Optional<Street> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return StreetUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public StreetDeleteByIdRs deleteById(StreetDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return StreetDeleteByIdRs.success();
    }

    private StreetGetRs searchResult(Optional<Street> found) {
        if (found.isPresent()) {
            WebStreet webStreet = mapper.fromEntity(found.get());

            return StreetGetRs.success(Collections.singletonList(webStreet));
        } else {
            return StreetGetRs.success(Collections.emptyList());
        }
    }
}

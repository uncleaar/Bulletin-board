package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.lnk.*;
import ru.gold.ordance.board.model.entity.domain.LnkLocalityStreet;
import ru.gold.ordance.board.model.entity.domain.Locality;
import ru.gold.ordance.board.model.entity.domain.Street;
import ru.gold.ordance.board.service.base.heir.LnkLocalityStreetService;
import ru.gold.ordance.board.web.service.base.LnkLocalityStreetWebService;
import ru.gold.ordance.board.web.service.mapper.LnkLocalityStreetMapper;
import ru.gold.ordance.board.web.service.mapper.impl.LnkLocalityStreetMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LnkLocalityStreetWebServiceImpl implements LnkLocalityStreetWebService {
    private final LnkLocalityStreetService service;

    private final LnkLocalityStreetMapper mapper = new LnkLocalityStreetMapperImpl();

    public LnkLocalityStreetWebServiceImpl(LnkLocalityStreetService service) {
        this.service = service;
    }

    @Override
    public LnkLocalityStreetGetRs findAll() {
        List<LnkLocalityStreet> found = service.findAll();

        return list(found);
    }

    @Override
    public LnkLocalityStreetGetRs findById(LnkLocalityStreetGetByIdRq rq) {
        Optional<LnkLocalityStreet> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public LnkLocalityStreetGetRs findByLocalityAndStreet(LnkLocalityStreetGetByLSRq rq) {
        Optional<LnkLocalityStreet> found =
                service.findByLocalityAndStreet(
                        Locality.builder().id(rq.getLocalityId()).build(),
                        Street.builder().id(rq.getStreetId()).build());

        return searchResult(found);
    }

    @Override
    public LnkLocalityStreetGetRs findByLocality(LnkLocalityStreetGetByLocalityRq rq) {
        List<LnkLocalityStreet> found = service.findByLocality(Locality.builder().id(rq.getLocalityId()).build());

        return list(found);
    }

    @Override
    public LnkLocalityStreetGetRs findByStreet(LnkLocalityStreetGetByStreetRq rq) {
        List<LnkLocalityStreet> found = service.findByStreet(Street.builder().id(rq.getStreetId()).build());

        return list(found);
    }

    @Override
    public LnkLocalityStreetUpdateRs update(LnkLocalityStreetUpdateRq rq) {
        Optional<LnkLocalityStreet> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return LnkLocalityStreetUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public LnkLocalityStreetDeleteByIdRs deleteById(LnkLocalityStreetDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return LnkLocalityStreetDeleteByIdRs.success();
    }

    private LnkLocalityStreetGetRs list(List<LnkLocalityStreet> list) {
        if (!list.isEmpty()) {
            List<WebLnkLocalityStreet> webLnkLocalityStreets = list.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return LnkLocalityStreetGetRs.success(webLnkLocalityStreets);
        } else {
            return LnkLocalityStreetGetRs.success(Collections.emptyList());
        }
    }

    private LnkLocalityStreetGetRs searchResult(Optional<LnkLocalityStreet> found) {
        if (found.isPresent()) {
            WebLnkLocalityStreet webLnkLocalityStreet = mapper.fromEntity(found.get());

            return LnkLocalityStreetGetRs.success(Collections.singletonList(webLnkLocalityStreet));
        } else {
            return LnkLocalityStreetGetRs.success(Collections.emptyList());
        }
    }
}

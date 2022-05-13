package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.web.api.locality.*;
import ru.gold.ordance.board.core.entity.Locality;
import ru.gold.ordance.board.core.service.heir.LocalityService;
import ru.gold.ordance.board.web.service.base.LocalityWebService;
import ru.gold.ordance.board.web.service.mapper.LocalityMapper;
import ru.gold.ordance.board.web.service.mapper.impl.LocalityMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalityWebServiceImpl implements LocalityWebService {
    private final LocalityService service;

    private final LocalityMapper mapper = new LocalityMapperImpl();

    public LocalityWebServiceImpl(LocalityService service) {
        this.service = service;
    }

    @Override
    public LocalityGetRs findAll() {
        List<Locality> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebLocality> webLocalities = found.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return LocalityGetRs.success(webLocalities);
        } else {
            return LocalityGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public LocalityGetRs findById(LocalityGetByIdRq rq) {
        Optional<Locality> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public LocalityGetRs findByName(LocalityGetByNameRq rq) {
        Optional<Locality> found = service.findByName(rq.getName());

        return searchResult(found);
    }

    @Override
    public LocalityUpdateRs update(LocalityUpdateRq rq) {
        Optional<Locality> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return LocalityUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public LocalityDeleteByIdRs deleteById(LocalityDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return LocalityDeleteByIdRs.success();
    }

    private LocalityGetRs searchResult(Optional<Locality> found) {
        if (found.isPresent()) {
            WebLocality webLocality = mapper.fromEntity(found.get());

            return LocalityGetRs.success(Collections.singletonList(webLocality));
        } else {
            return LocalityGetRs.success(Collections.emptyList());
        }
    }
}

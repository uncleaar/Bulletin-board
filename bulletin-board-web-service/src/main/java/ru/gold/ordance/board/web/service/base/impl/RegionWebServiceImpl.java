package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.region.*;
import ru.gold.ordance.board.model.entity.domain.Region;
import ru.gold.ordance.board.service.base.heir.RegionService;
import ru.gold.ordance.board.web.service.base.RegionWebService;
import ru.gold.ordance.board.web.service.mapper.RegionMapper;
import ru.gold.ordance.board.web.service.mapper.impl.RegionMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegionWebServiceImpl implements RegionWebService {
    private final RegionService service;

    private final RegionMapper mapper = new RegionMapperImpl();

    public RegionWebServiceImpl(RegionService service) {
        this.service = service;
    }

    @Override
    public RegionGetRs findAll() {
        List<Region> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebRegion> webRegions = found.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return RegionGetRs.success(webRegions);
        } else {
            return RegionGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public RegionGetRs findById(RegionGetByIdRq rq) {
        Optional<Region> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public RegionGetRs findByName(RegionGetByNameRq rq) {
        Optional<Region> found = service.findByName(rq.getName());

        return searchResult(found);
    }

    @Override
    public RegionUpdateRs update(RegionUpdateRq rq) {
        Optional<Region> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return RegionUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public RegionDeleteByIdRs deleteById(RegionDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return RegionDeleteByIdRs.success();
    }

    private RegionGetRs searchResult(Optional<Region> found) {
        if (found.isPresent()) {
            WebRegion webRegion = mapper.fromEntity(found.get());

            return RegionGetRs.success(Collections.singletonList(webRegion));
        } else {
            return RegionGetRs.success(Collections.emptyList());
        }
    }
}

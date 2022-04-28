package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.advertisement.*;
import ru.gold.ordance.board.model.entity.domain.Advertisement;
import ru.gold.ordance.board.service.base.heir.AdvertisementService;
import ru.gold.ordance.board.web.service.base.AdvertisementWebService;
import ru.gold.ordance.board.web.service.mapper.AdvertisementMapper;
import ru.gold.ordance.board.web.service.mapper.impl.AdvertisementMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementWebServiceImpl implements AdvertisementWebService {
    private final AdvertisementService service;

    private final AdvertisementMapper mapper = new AdvertisementMapperImpl();

    public AdvertisementWebServiceImpl(AdvertisementService service) {
        this.service = service;
    }

    @Override
    public AdvertisementGetRs findAll() {
        List<Advertisement> found = service.findAll();

        return list(found);
    }

    @Override
    public AdvertisementGetRs findById(AdvertisementGetByIdRq rq) {
        Optional<Advertisement> found = service.findById(rq.getEntityId());

        if (found.isPresent()) {
            WebAdvertisement webAdvertisementRs = mapper.fromEntity(found.get());

            return AdvertisementGetRs.success(Collections.singletonList(webAdvertisementRs));
        } else {
            return AdvertisementGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public AdvertisementGetRs findByName(AdvertisementGetByNameRq rq) {
        List<Advertisement> found = service.findAllByName(rq.getName());

        return list(found);
    }

    @Override
    public AdvertisementUpdateRs update(AdvertisementUpdateRq rq) {
        Optional<Advertisement> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return AdvertisementUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public AdvertisementDeleteByIdRs deleteById(AdvertisementDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return AdvertisementDeleteByIdRs.success();
    }

    private AdvertisementGetRs list(List<Advertisement> list) {
        if (!list.isEmpty()) {
            List<WebAdvertisement> webAdvertisements = list.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return AdvertisementGetRs.success(webAdvertisements);
        } else {
            return AdvertisementGetRs.success(Collections.emptyList());
        }
    }
}

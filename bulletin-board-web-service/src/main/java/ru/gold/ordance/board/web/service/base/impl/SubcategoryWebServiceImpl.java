package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.api.domain.subcategory.*;
import ru.gold.ordance.board.model.entity.domain.Subcategory;
import ru.gold.ordance.board.service.base.heir.SubcategoryService;
import ru.gold.ordance.board.web.service.base.SubcategoryWebService;
import ru.gold.ordance.board.web.service.mapper.SubcategoryMapper;
import ru.gold.ordance.board.web.service.mapper.impl.SubcategoryMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubcategoryWebServiceImpl implements SubcategoryWebService {
    private final SubcategoryService service;

    private final SubcategoryMapper mapper = new SubcategoryMapperImpl();

    public SubcategoryWebServiceImpl(SubcategoryService service) {
        this.service = service;
    }

    @Override
    public SubcategoryGetRs findAll() {
        List<Subcategory> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebSubcategory> webRegions = found.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return SubcategoryGetRs.success(webRegions);
        } else {
            return SubcategoryGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public SubcategoryGetRs findById(SubcategoryGetByIdRq rq) {
        Optional<Subcategory> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public SubcategoryGetRs findByName(SubcategoryGetByNameRq rq) {
        Optional<Subcategory> found = service.findByName(rq.getName());

        return searchResult(found);
    }

    @Override
    public SubcategoryUpdateRs update(SubcategoryUpdateRq rq) {
        Optional<Subcategory> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return SubcategoryUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public SubcategoryDeleteByIdRs deleteById(SubcategoryDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return SubcategoryDeleteByIdRs.success();
    }

    private SubcategoryGetRs searchResult(Optional<Subcategory> found) {
        if (found.isPresent()) {
            WebSubcategory webSubcategory = mapper.fromEntity(found.get());

            return SubcategoryGetRs.success(Collections.singletonList(webSubcategory));
        } else {
            return SubcategoryGetRs.success(Collections.emptyList());
        }
    }
}

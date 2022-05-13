package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.web.api.category.*;
import ru.gold.ordance.board.core.entity.Category;
import ru.gold.ordance.board.core.service.heir.CategoryService;
import ru.gold.ordance.board.web.service.base.CategoryWebService;
import ru.gold.ordance.board.web.service.mapper.CategoryMapper;
import ru.gold.ordance.board.web.service.mapper.impl.CategoryMapperImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryWebServiceImpl implements CategoryWebService {
    private final CategoryService service;

    private final CategoryMapper mapper = new CategoryMapperImpl();

    public CategoryWebServiceImpl(CategoryService service) {
        this.service = service;
    }

    @Override
    public CategoryGetRs findAll() {
        List<Category> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebCategory> webCategories = found.stream()
                    .map(mapper::fromEntity)
                    .collect(Collectors.toList());

            return CategoryGetRs.success(webCategories);
        } else {
            return CategoryGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public CategoryGetRs findById(CategoryGetByIdRq rq) {
        Optional<Category> found = service.findById(rq.getEntityId());

        return searchResult(found);
    }

    @Override
    public CategoryGetRs findByName(CategoryGetByNameRq rq) {
        Optional<Category> found = service.findByName(rq.getName());

        return searchResult(found);
    }

    @Override
    public CategoryUpdateRs update(CategoryUpdateRq rq) {
        Optional<Category> updated = service.update(mapper.fromRequest(rq));
        boolean isPresent = updated.isPresent();

        return CategoryUpdateRs.success(isPresent, isPresent ? updated.get().getId() : null);
    }

    @Override
    public CategoryDeleteByIdRs deleteById(CategoryDeleteByIdRq rq) {
        service.deleteById(rq.getEntityId());

        return CategoryDeleteByIdRs.success();
    }

    private CategoryGetRs searchResult(Optional<Category> found) {
        if (found.isPresent()) {
            WebCategory webCategory = mapper.fromEntity(found.get());

            return CategoryGetRs.success(Collections.singletonList(webCategory));
        } else {
            return CategoryGetRs.success(Collections.emptyList());
        }
    }
}

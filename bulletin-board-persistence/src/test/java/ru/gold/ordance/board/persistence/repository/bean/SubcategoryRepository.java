package ru.gold.ordance.board.persistence.repository.bean;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.domain.Subcategory;
import ru.gold.ordance.board.persistence.repository.SearchByNameRepository;

@Repository
public interface SubcategoryRepository extends SearchByNameRepository<Subcategory> {
}

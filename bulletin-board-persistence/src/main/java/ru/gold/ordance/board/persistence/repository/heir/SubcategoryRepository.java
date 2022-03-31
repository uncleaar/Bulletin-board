package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.domain.Subcategory;
import ru.gold.ordance.board.persistence.repository.SearchByNameRepository;

@Repository
public interface SubcategoryRepository extends SearchByNameRepository<Subcategory> {
}

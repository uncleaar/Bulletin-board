package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.Subcategory;
import ru.gold.ordance.board.core.persistence.SearchByNameRepository;

@Repository
public interface SubcategoryRepository extends SearchByNameRepository<Subcategory> {
}

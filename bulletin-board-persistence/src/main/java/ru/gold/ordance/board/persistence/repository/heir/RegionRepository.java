package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.domain.Region;
import ru.gold.ordance.board.persistence.repository.SearchByNameRepository;

@Repository
public interface RegionRepository extends SearchByNameRepository<Region> {
}

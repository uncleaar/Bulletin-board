package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.Region;
import ru.gold.ordance.board.core.persistence.SearchByNameRepository;

@Repository
public interface RegionRepository extends SearchByNameRepository<Region> {
}

package ru.gold.ordance.board.persistence.repository.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.model.entity.domain.Advertisement;
import ru.gold.ordance.board.persistence.repository.SearchAllByNameRepository;

@Repository
public interface AdvertisementRepository extends SearchAllByNameRepository<Advertisement> {
}

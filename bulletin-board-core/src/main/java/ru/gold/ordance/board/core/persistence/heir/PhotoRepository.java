package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.Photo;
import ru.gold.ordance.board.core.persistence.EntityRepository;

@Repository
public interface PhotoRepository extends EntityRepository<Photo> {
}

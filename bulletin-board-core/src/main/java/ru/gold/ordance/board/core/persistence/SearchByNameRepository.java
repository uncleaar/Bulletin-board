package ru.gold.ordance.board.core.persistence;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface SearchByNameRepository<Entity> extends EntityRepository<Entity> {
    Optional<Entity> findByNameIgnoreCase(String name);
    List<Entity> findByNameIgnoreCaseContaining(String name);
}

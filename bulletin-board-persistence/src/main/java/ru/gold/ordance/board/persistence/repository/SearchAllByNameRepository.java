package ru.gold.ordance.board.persistence.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface SearchAllByNameRepository<Entity> extends EntityRepository<Entity> {
    List<Entity> findByNameIgnoreCaseContaining(String name);
}

package ru.gold.ordance.board.core.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EntityRepository<Entity> extends JpaRepository<Entity, Long> {
    Optional<Entity> findById(Long id);
    List<Entity> findAll();
}

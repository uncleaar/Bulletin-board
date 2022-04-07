package ru.gold.ordance.board.service.base;

import java.util.List;
import java.util.Optional;

public interface AbstractService<MODEL> {
    List<MODEL> findAll();
    Optional<MODEL> findById(Long id);
    Optional<MODEL> update(MODEL model);
    void delete(MODEL model);
    void deleteById(Long id);
}
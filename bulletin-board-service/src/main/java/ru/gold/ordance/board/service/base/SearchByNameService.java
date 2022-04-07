package ru.gold.ordance.board.service.base;

import java.util.Optional;

public interface SearchByNameService<MODEL> extends AbstractService<MODEL> {
    Optional<MODEL> findByName(String name);
}

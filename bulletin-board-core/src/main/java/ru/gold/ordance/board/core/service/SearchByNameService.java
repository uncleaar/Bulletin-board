package ru.gold.ordance.board.core.service;

import java.util.Optional;

public interface SearchByNameService<MODEL> extends AbstractService<MODEL> {
    Optional<MODEL> findByName(String name);
}

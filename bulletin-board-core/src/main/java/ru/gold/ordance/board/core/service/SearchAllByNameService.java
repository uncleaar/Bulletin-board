package ru.gold.ordance.board.core.service;

import java.util.List;

public interface SearchAllByNameService<MODEL> extends AbstractService<MODEL> {
    List<MODEL> findAllByName(String name);
}

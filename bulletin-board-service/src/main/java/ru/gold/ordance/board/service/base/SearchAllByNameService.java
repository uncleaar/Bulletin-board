package ru.gold.ordance.board.service.base;

import java.util.List;

public interface SearchAllByNameService<MODEL> extends AbstractService<MODEL> {
    List<MODEL> findAllByName(String name);
}

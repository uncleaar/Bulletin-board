package ru.gold.ordance.board.api.rest;

public interface AbstractDeleteRestController<RS> {
    RS deleteById(Long entityId);
}

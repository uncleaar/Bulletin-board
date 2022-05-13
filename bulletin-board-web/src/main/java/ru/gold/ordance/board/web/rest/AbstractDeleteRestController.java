package ru.gold.ordance.board.web.rest;

public interface AbstractDeleteRestController<RS> {
    RS deleteById(Long entityId);
}

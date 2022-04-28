package ru.gold.ordance.board.api.rest;

public interface AbstractBaseSearchRestController<RS> {
    RS findAll();
    RS findById(Long entityId);
}


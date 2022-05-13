package ru.gold.ordance.board.web.rest;

public interface AbstractBaseSearchRestController<RS> {
    RS findAll();
    RS findById(Long entityId);
}


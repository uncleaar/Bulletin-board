package ru.gold.ordance.board.api.rest;

public interface AbstractUpdateRestController<RQ, RS> {
    RS update(RQ rq);
}

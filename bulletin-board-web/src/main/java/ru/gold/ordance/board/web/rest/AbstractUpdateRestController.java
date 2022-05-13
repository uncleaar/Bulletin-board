package ru.gold.ordance.board.web.rest;

public interface AbstractUpdateRestController<RQ, RS> {
    RS update(RQ rq);
}

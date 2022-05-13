package ru.gold.ordance.board.web.validation;

public interface RequestValidation<T> {
    void validate(T request);
}

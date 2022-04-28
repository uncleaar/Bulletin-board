package ru.gold.ordance.board.api.validation;

public interface RequestValidation<T> {
    void validate(T request);
}

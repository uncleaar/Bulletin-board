package ru.gold.ordance.board.model.api.domain.locality;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetByNameRq;

@AllArgsConstructor
@Getter
@ToString
public class LocalityGetByNameRq implements GetByNameRq {
    private final String name;
}
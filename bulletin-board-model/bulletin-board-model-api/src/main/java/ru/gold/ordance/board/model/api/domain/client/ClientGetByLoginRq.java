package ru.gold.ordance.board.model.api.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.GetRq;

@AllArgsConstructor
@Getter
@ToString
public class ClientGetByLoginRq implements GetRq {
    private final String login;
}

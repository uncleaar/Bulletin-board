package ru.gold.ordance.board.model.api.domain.client;

import lombok.*;
import ru.gold.ordance.board.model.api.domain.SaveRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClientSaveRq implements SaveRq {
    private final String login;

    private final String password;

    private final String name;

    private final String phoneNumber;
}

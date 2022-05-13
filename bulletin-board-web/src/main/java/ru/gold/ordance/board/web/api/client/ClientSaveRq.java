package ru.gold.ordance.board.web.api.client;

import lombok.*;
import ru.gold.ordance.board.web.api.SaveRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class ClientSaveRq implements SaveRq {
    private static final long serialVersionUID = 1L;

    private final String login;

    private final String password;

    private final String name;

    private final String phoneNumber;
}

package ru.gold.ordance.board.model.api.domain.client;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebClient {
    private final Long entityId;

    private final String login;

    private final String name;

    private final String phoneNumber;
}


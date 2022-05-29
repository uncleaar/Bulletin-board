package ru.gold.ordance.board.web.api.client;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebClient {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String login;

    private final String name;

    private final String phoneNumber;

    private final String role;
}


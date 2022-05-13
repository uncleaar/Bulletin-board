package ru.gold.ordance.board.web.api.street;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebStreet {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;
}

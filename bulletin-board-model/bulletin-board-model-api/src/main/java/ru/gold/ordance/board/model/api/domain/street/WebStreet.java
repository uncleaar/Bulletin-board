package ru.gold.ordance.board.model.api.domain.street;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebStreet {
    private final Long entityId;

    private final String name;
}

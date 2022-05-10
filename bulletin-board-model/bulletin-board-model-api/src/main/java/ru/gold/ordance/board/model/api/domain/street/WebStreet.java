package ru.gold.ordance.board.model.api.domain.street;

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

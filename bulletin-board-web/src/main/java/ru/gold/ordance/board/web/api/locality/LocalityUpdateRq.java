package ru.gold.ordance.board.web.api.locality;

import lombok.*;
import ru.gold.ordance.board.web.api.UpdateRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class LocalityUpdateRq implements UpdateRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;

    private final Long regionId;
}
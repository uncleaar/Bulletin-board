package ru.gold.ordance.board.model.api.domain.locality;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString
@Getter
public class WebLocality {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;

    private final Long regionId;
}

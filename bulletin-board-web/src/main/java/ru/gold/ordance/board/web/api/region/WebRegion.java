package ru.gold.ordance.board.web.api.region;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebRegion {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String name;
}

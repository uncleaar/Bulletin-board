package ru.gold.ordance.board.web.api.photo;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class WebPhoto implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Long entityId;

    private final String urn;
}
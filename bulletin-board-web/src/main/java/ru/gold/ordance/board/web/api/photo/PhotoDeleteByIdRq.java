package ru.gold.ordance.board.web.api.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.DeleteByIdRq;

@AllArgsConstructor
@Getter
@ToString
public class PhotoDeleteByIdRq implements DeleteByIdRq {
    private static final long serialVersionUID = 1L;

    private final Long entityId;
}

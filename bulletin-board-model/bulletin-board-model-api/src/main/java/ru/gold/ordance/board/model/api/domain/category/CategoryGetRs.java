package ru.gold.ordance.board.model.api.domain.category;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.model.api.domain.Rs;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class CategoryGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebCategory> categoryList;

    private final Integer total;

    public static CategoryGetRs success(List<WebCategory> categoryList) {
        return CategoryGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .categoryList(categoryList)
                .total(categoryList.size())
                .build();
    }

    public static CategoryGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return CategoryGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

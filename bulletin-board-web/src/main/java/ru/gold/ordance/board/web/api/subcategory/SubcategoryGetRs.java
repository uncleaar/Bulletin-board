package ru.gold.ordance.board.web.api.subcategory;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

import java.util.List;

@Builder
@Getter
@ToString
public class SubcategoryGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebSubcategory> subcategoryList;

    private final Integer total;

    public static SubcategoryGetRs success(List<WebSubcategory> subcategoryList) {
        return SubcategoryGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .subcategoryList(subcategoryList)
                .total(subcategoryList.size())
                .build();
    }

    public static SubcategoryGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return SubcategoryGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

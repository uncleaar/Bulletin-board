package ru.gold.ordance.board.web.api.photo;

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
public class PhotoGetRs implements Rs {
    private static final long serialVersionUID = 1L;

    private final Status status;

    private final List<WebPhoto> photoList;

    private final Integer total;

    public static PhotoGetRs success(List<WebPhoto> photoList) {
        return PhotoGetRs.builder()
                .status(new Status().withCode(StatusCode.SUCCESS))
                .photoList(photoList)
                .total(photoList.size())
                .build();
    }

    public static PhotoGetRs error(StatusCode code, String description) {
        if (code == StatusCode.SUCCESS) {
            throw new IllegalArgumentException("The transmitted code not should equal SUCCESS.");
        }

        return PhotoGetRs.builder()
                .status(new Status().withCode(code).withDescription(description))
                .build();
    }
}

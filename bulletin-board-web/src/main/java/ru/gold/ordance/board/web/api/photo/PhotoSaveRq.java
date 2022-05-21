package ru.gold.ordance.board.web.api.photo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.board.web.api.SaveRq;

@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@ToString
public class PhotoSaveRq implements SaveRq {
    private static final long serialVersionUID = 1L;

    private final MultipartFile file;
}

package ru.gold.ordance.board.web.rest.heir.base;

import org.springframework.web.multipart.MultipartFile;
import ru.gold.ordance.board.web.api.photo.*;
import ru.gold.ordance.board.web.rest.AbstractDeleteRestController;

public interface PhotoRestController extends AbstractDeleteRestController<PhotoDeleteByIdRs> {
    PhotoSaveRs save(MultipartFile file);
}

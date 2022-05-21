package ru.gold.ordance.board.web.service.base;

import ru.gold.ordance.board.web.api.photo.*;

import java.io.IOException;

public interface PhotoWebService {
    PhotoGetRs findAll();
    PhotoGetRs findById(PhotoGetByIdRq rq);
    PhotoSaveRs save(PhotoSaveRq rq) throws IOException;
    PhotoDeleteByIdRs deleteById(PhotoDeleteByIdRq rq) throws IOException;
}

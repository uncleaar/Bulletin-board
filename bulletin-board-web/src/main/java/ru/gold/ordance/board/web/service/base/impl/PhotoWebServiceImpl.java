package ru.gold.ordance.board.web.service.base.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.core.entity.Photo;
import ru.gold.ordance.board.core.service.heir.PhotoService;
import ru.gold.ordance.board.web.api.photo.*;
import ru.gold.ordance.board.web.service.base.PhotoWebService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.gold.ordance.board.web.utils.PhotoStorageUtils.deleteFile;
import static ru.gold.ordance.board.web.utils.PhotoStorageUtils.moveFile;

@Service
public class PhotoWebServiceImpl implements PhotoWebService {
    private final PhotoService service;

    public PhotoWebServiceImpl(PhotoService service) {
        this.service = service;
    }

    @Override
    public PhotoGetRs findAll() {
        List<Photo> found = service.findAll();

        if (!found.isEmpty()) {
            List<WebPhoto> webPhotos = found.stream()
                    .map(p -> WebPhoto.builder()
                            .entityId(p.getId())
                            .urn(p.getUrn())
                            .build())
                    .collect(Collectors.toList());

            return PhotoGetRs.success(webPhotos);
        } else {
            return PhotoGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public PhotoGetRs findById(PhotoGetByIdRq rq) {
        Optional<Photo> found = service.findById(rq.getEntityId());

        if (found.isPresent()) {
            return PhotoGetRs.success(Collections.singletonList(WebPhoto.builder()
                    .entityId(found.get().getId())
                    .urn(found.get().getUrn())
                    .build()));
        } else {
            return PhotoGetRs.success(Collections.emptyList());
        }
    }

    @Override
    public PhotoSaveRs save(PhotoSaveRq rq) throws IOException {
        Photo savedPhoto = service.update(Photo.builder()
                .urn(moveFile(rq.getFile()))
                .build())
                .get();

        return PhotoSaveRs.success(savedPhoto.getId(), savedPhoto.getUrn());
    }

    @Override
    public PhotoDeleteByIdRs deleteById(PhotoDeleteByIdRq rq) throws IOException {
        Optional<Photo> photo = service.findById(rq.getEntityId());

        if (photo.isPresent()) {
            deleteFile(photo.get().getUrn());
        }

        service.deleteById(rq.getEntityId());

        return PhotoDeleteByIdRs.success();
    }
}

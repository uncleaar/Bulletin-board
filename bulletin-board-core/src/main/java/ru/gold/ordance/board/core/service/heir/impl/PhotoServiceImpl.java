package ru.gold.ordance.board.core.service.heir.impl;

import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gold.ordance.board.core.entity.Photo;
import ru.gold.ordance.board.core.persistence.heir.PhotoRepository;
import ru.gold.ordance.board.core.service.heir.PhotoService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class PhotoServiceImpl implements PhotoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhotoServiceImpl.class);

    private final PhotoRepository repository;

    public PhotoServiceImpl(PhotoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Photo> findAll() {
        LOGGER.info("The search for all photos has started.");

        List<Photo> photos = repository.findAll();

        LOGGER.info("Size of list: {}", photos.size());

        return photos;
    }

    @Override
    public Optional<Photo> findById(@NotNull Long id) {
        LOGGER.info("The search by id photo has started.");

        Optional<Photo> photo = repository.findById(id);

        LOGGER.info("The photo " + (photo.isEmpty() ? "not found" : "was found") + ". entityId = {}", id);

        return photo;
    }

    @Override
    public Optional<Photo> update(@NotNull Photo photo) {
        LOGGER.info("Update photo has started.");

        Photo updatedRegion = repository.saveAndFlush(photo);

        LOGGER.info("The photo was updated.");

        return Optional.of(updatedRegion);
    }

    @Override
    public void deleteById(@NotNull Long id) {
        LOGGER.info("Delete photo has started.");

        Optional<Photo> found = repository.findById(id);

        if (found.isPresent()) {
            repository.deleteById(id);
            LOGGER.info("The photo was deleted. entityId = {}", id);
        } else {
            LOGGER.info("The photo does not exist. entityId = {}", id);
        }
    }
}

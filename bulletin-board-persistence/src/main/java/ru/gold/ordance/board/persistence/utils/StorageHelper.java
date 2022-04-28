package ru.gold.ordance.board.persistence.utils;

import org.springframework.stereotype.Component;
import ru.gold.ordance.board.model.entity.domain.*;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
public class StorageHelper {
    private final EntityManager manager;

    public StorageHelper(EntityManager manager) {
        this.manager = manager;
    }

    public boolean exists(AbstractEntity entity) {
        Optional<? extends AbstractEntity> obj = entity.getId() == null
                ? Optional.empty()
                : find(entity.getClass(), entity.getId());

        return obj.isPresent();
    }

    public boolean existsExternal(AbstractEntity entity) {
        boolean exists;

        if (Advertisement.class.isAssignableFrom(entity.getClass())) {
            exists = exists((Advertisement) entity);
        } else if (LnkLocalityStreet.class.isAssignableFrom(entity.getClass())) {
            exists = exists((LnkLocalityStreet) entity);
        } else if (Address.class.isAssignableFrom(entity.getClass())) {
            exists = exists((Address) entity);
        } else if (Locality.class.isAssignableFrom(entity.getClass())) {
            exists = exists((Locality) entity);
        } else if (Subcategory.class.isAssignableFrom(entity.getClass())) {
            exists = exists((Subcategory) entity);
        } else {
            throw new IllegalArgumentException("The passed entity not supported by the current method.");
        }

        return exists;
    }

    public <T> Optional<T> find(Class<T> clazz, Long id) {
        return Optional.ofNullable(manager.find(clazz, id));
    }

    private boolean exists(Advertisement o) {
        Optional<Client> client = find(Client.class, o.getClient().getId());
        Optional<Subcategory> subcategory = find(Subcategory.class, o.getSubcategory().getId());
        Optional<Locality> locality = find(Locality.class, o.getLocality().getId());
        Optional<Street> street = find(Street.class, o.getStreet().getId());

        return client.isPresent()
                && subcategory.isPresent()
                && existsExternal(subcategory.get())
                && street.isPresent()
                && locality.isPresent()
                && existsExternal(locality.get());
    }

    private boolean exists(LnkLocalityStreet o) {
        Optional<Locality> locality = find(Locality.class, o.getLocality().getId());
        Optional<Street> street = find(Street.class, o.getStreet().getId());

        return street.isPresent()
                && locality.isPresent()
                && existsExternal(locality.get());
    }

    private boolean exists(Address o) {
        Optional<Locality> locality = find(Locality.class, o.getLocality().getId());
        Optional<Street> street = find(Street.class, o.getStreet().getId());

        return street.isPresent()
                && locality.isPresent()
                && existsExternal(locality.get());
    }

    private boolean exists(Locality o) {
        Optional<Region> region = find(Region.class, o.getRegion().getId());

        return region.isPresent();
    }

    private boolean exists(Subcategory o) {
        Optional<Category> category = find(Category.class, o.getCategory().getId());

        return category.isPresent();
    }
}

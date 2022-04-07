package ru.gold.ordance.board.persistence.utils;

import org.springframework.stereotype.Component;
import ru.gold.ordance.board.model.entity.AbstractEntity;
import ru.gold.ordance.board.model.entity.authorization.Client;
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
            Advertisement o = (Advertisement) entity;
            Optional<Client> client = find(Client.class, o.getClient().getId());
            Optional<Subcategory> subcategory = find(Subcategory.class, o.getSubcategory().getId());
            Optional<Locality> locality = find(Locality.class, o.getLocality().getId());
            Optional<Street> street = find(Street.class, o.getStreet().getId());

            exists = client.isPresent()
                    && subcategory.isPresent()
                    && existsExternal(subcategory.get())
                    && street.isPresent()
                    && locality.isPresent()
                    && existsExternal(locality.get());
        } else if (LnkLocalityStreet.class.isAssignableFrom(entity.getClass())) {
            LnkLocalityStreet o = (LnkLocalityStreet) entity;
            Optional<Locality> locality = find(Locality.class, o.getLocality().getId());
            Optional<Street> street = find(Street.class, o.getStreet().getId());

            exists = street.isPresent() && locality.isPresent() && existsExternal(locality.get());
        } else if (Address.class.isAssignableFrom(entity.getClass())) {
            Address o = (Address) entity;
            Optional<Locality> locality = find(Locality.class, o.getLocality().getId());
            Optional<Street> street = find(Street.class, o.getStreet().getId());

            exists = street.isPresent() && locality.isPresent() && existsExternal(locality.get());
        } else if (Locality.class.isAssignableFrom(entity.getClass())) {
            Locality o = (Locality) entity;
            Optional<Region> region = find(Region.class, o.getRegion().getId());

            exists = region.isPresent();
        } else if (Subcategory.class.isAssignableFrom(entity.getClass())) {
            Subcategory o = (Subcategory) entity;
            Optional<Category> category = find(Category.class, o.getCategory().getId());

            exists = category.isPresent();
        } else {
            throw new IllegalArgumentException("The passed entity not supported by the current method.");
        }

        return exists;
    }

    private <T> Optional<T> find(Class<T> clazz, Long id) {
        return Optional.ofNullable(manager.find(clazz, id));
    }
}

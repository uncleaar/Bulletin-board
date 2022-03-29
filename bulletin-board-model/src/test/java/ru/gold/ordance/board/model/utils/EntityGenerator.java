package ru.gold.ordance.board.model.utils;

import ru.gold.ordance.board.model.authorization.Client;
import ru.gold.ordance.board.model.authorization.Role;
import ru.gold.ordance.board.model.domain.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class EntityGenerator {
    private EntityGenerator() {
    }

    public static Category createCategoryWithoutSubcategories() {
        return Category.builder()
                .name(UUID.randomUUID().toString())
                .build();
    }

    public static Category createCategory(Set<Subcategory> subcategories) {
        return Category.builder()
                .name(UUID.randomUUID().toString())
                .subcategories(subcategories)
                .build();
    }

    public static Subcategory createSubcategory() {
        return Subcategory.builder()
                .name(UUID.randomUUID().toString())
                .category(createCategoryWithoutSubcategories())
                .build();
    }

    public static Subcategory createSubcategoryWithoutCategory() {
        return Subcategory.builder()
                .name(UUID.randomUUID().toString())
                .build();
    }

    public static Subcategory createSubcategory(Category category) {
        return Subcategory.builder()
                .name(UUID.randomUUID().toString())
                .category(category)
                .build();
    }

    public static Locality createLocality() {
        return Locality.builder()
                .name(UUID.randomUUID().toString())
                .region(createRegion())
                .build();
    }

    public static Locality createLocality(Region region) {
        return Locality.builder()
                .name(UUID.randomUUID().toString())
                .region(region)
                .build();
    }

    public static Street createStreet() {
        return Street.builder()
                .name(UUID.randomUUID().toString())
                .build();
    }

    public static Region createRegion() {
        return Region.builder()
                .name(UUID.randomUUID().toString())
                .build();
    }

    public static LnkLocalityStreet createLnkLocalityStreet() {
        return LnkLocalityStreet.builder()
                .locality(createLocality())
                .street(createStreet())
                .build();
    }

    public static LnkLocalityStreet createLnkLocalityStreet(Locality locality, Street street) {
        return LnkLocalityStreet.builder()
                .locality(locality)
                .street(street)
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .locality(createLocality())
                .street(createStreet())
                .houseNumber(UUID.randomUUID().toString())
                .build();
    }

    public static Address createAddress(Locality locality, Street street) {
        return Address.builder()
                .locality(locality)
                .street(street)
                .houseNumber(UUID.randomUUID().toString())
                .build();
    }

    public static Advertisement createAdvertisementWithOnlyClient(Client client) {
        return createBaseAdvertisement()
                .client(client)
                .build();
    }

    public static Advertisement createAdvertisementWithOnlySubcategory(Subcategory subcategory) {
        return createBaseAdvertisement()
                .subcategory(subcategory)
                .build();
    }

    public static Advertisement createAdvertisementWithOnlyLocality(Locality locality) {
        return createBaseAdvertisement()
                .locality(locality)
                .build();
    }

    public static Advertisement createAdvertisementWithOnlyStreet(Street street) {
        return createBaseAdvertisement()
                .street(street)
                .build();
    }

    public static Client createClient() {
        return Client.builder()
                .login(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .salt(UUID.randomUUID().toString())
                .role(Role.values()[ThreadLocalRandom.current().nextInt(0, Role.values().length)])
                .name(UUID.randomUUID().toString())
                .phoneNumber(UUID.randomUUID().toString())
                .build();
    }

    private static Advertisement.AdvertisementBuilder createBaseAdvertisement() {
        return Advertisement.builder()
                .name(UUID.randomUUID().toString())
                .createDate(LocalDate.now())
                .description(UUID.randomUUID().toString())
                .price(ThreadLocalRandom.current().nextInt())
                .houseNumber(UUID.randomUUID().toString());
    }
}

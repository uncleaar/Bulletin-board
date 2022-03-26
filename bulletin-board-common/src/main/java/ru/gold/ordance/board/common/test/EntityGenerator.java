package ru.gold.ordance.board.common.test;

import ru.gold.ordance.board.model.authorization.Client;
import ru.gold.ordance.board.model.authorization.ClientData;
import ru.gold.ordance.board.model.domain.*;

import java.util.Set;

public final class EntityGenerator {
    private EntityGenerator() {
    }

    public static Address createAddress() {
        return Address.builder()
                .region(createRegion())
                .locality(createLocality())
                .street(createStreet())
                .build();
    }

    public static Address createAddress(Region region) {
        return Address.builder()
                .region(region)
                .build();
    }

    public static Address createAddress(Locality locality) {
        return Address.builder()
                .locality(locality)
                .build();
    }

    public static Address createAddress(Street street) {
        return Address.builder()
                .street(street)
                .build();
    }

    public static Region createRegion() {
        return Region.builder()
                .build();
    }

    public static Locality createLocality() {
        return Locality.builder()
                .build();
    }

    public static Street createStreet() {
        return Street.builder()
                .build();
    }

    public static ClientData createClientData(Client client) {
        return ClientData.builder()
                .client(client)
                .build();
    }

    public static Client createClient() {
        return Client.builder()
                .build();
    }

    public static Category createCategory(Set<Subcategory> subcategories) {
        return Category.builder()
                .subcategories(subcategories)
                .build();
    }

    public static Subcategory createSubcategory() {
        return Subcategory.builder()
                .build();
    }

    public static Set<Subcategory> createSubcategories() {
        return Set.of(createSubcategory());
    }

    public static Advertisement createAdvertisement(Subcategory subcategory) {
        return Advertisement.builder()
                .subcategory(subcategory)
                .build();
    }

    public static Advertisement createAdvertisement(Address address) {
        return Advertisement.builder()
                .address(address)
                .build();
    }

    public static Advertisement createAdvertisement(ClientData clientData) {
        return Advertisement.builder()
                .clientData(clientData)
                .build();
    }
}

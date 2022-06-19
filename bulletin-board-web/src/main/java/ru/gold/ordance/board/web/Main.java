package ru.gold.ordance.board.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gold.ordance.board.core.entity.Photo;
import ru.gold.ordance.board.core.persistence.heir.PhotoRepository;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.web.api.auth.AuthRegistrationRq;
import ru.gold.ordance.board.web.api.auth.AuthRegistrationRs;
import ru.gold.ordance.board.web.api.category.CategoryUpdateRq;
import ru.gold.ordance.board.web.api.category.CategoryUpdateRs;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRs;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRs;
import ru.gold.ordance.board.web.service.auth.AuthWebService;
import ru.gold.ordance.board.web.service.base.AdvertisementWebService;
import ru.gold.ordance.board.web.service.base.CategoryWebService;
import ru.gold.ordance.board.web.service.base.SubcategoryWebService;
import ru.gold.ordance.board.web.service.complex.ComplexAddressWebService;

import java.io.File;

@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private CategoryWebService categoryWebService;

    @Autowired
    private SubcategoryWebService subcategoryWebService;

    @Autowired
    private AuthWebService authWebService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AdvertisementWebService advertisementWebService;

    @Autowired
    private ComplexAddressWebService complexAddressWebService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CategoryUpdateRs category = categoryWebService.update(CategoryUpdateRq.builder()
                .name("category_1")
                .build());

        SubcategoryUpdateRs subcategory = subcategoryWebService.update(SubcategoryUpdateRq.builder()
                .name("subcategory_1")
                .categoryId(category.getEntityId())
                .build());


        ComplexAddressUpdateRs address = complexAddressWebService.update(ComplexAddressUpdateRq.builder()
                .regionName("region_1")
                .localityName("locality_1")
                .streetName("street_1")
                .houseNumber("houseNumber_1")
                .build());

        AuthRegistrationRs registrationRs = authWebService.registration(AuthRegistrationRq.builder()
                .login("login")
                .password("password")
                .phoneNumber("phone")
                .name("name")
                .build());

        File file = new File("bulletin-board-web/src/main/resources/photo/photo_1.jpg");
        photoRepository.save(Photo.builder()
                .urn(file.getAbsolutePath())
                .build());

        advertisementWebService.update(AdvertisementUpdateRq.builder()
                .clientId(registrationRs.getWebClient().getEntityId())
                .name("advertisement_1")
                .subcategoryId(subcategory.getEntityId())
                .description("description_1")
                .price(10)
                .localityId(address.getLocalityId())
                .streetId(address.getStreetId())
                .houseNumber("houseNumber_1")
                .photoId(1L)
                .build());

        advertisementWebService.update(AdvertisementUpdateRq.builder()
                .clientId(registrationRs.getWebClient().getEntityId())
                .name("advertisement_2")
                .subcategoryId(subcategory.getEntityId())
                .description("description_2")
                .price(15)
                .localityId(address.getLocalityId())
                .streetId(address.getStreetId())
                .houseNumber("houseNumber_1")
                .photoId(1L)
                .build());
    }
}

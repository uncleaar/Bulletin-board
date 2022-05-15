package ru.gold.ordance.board.web.rest.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.board.web.Main;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementGetByIdRq;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementGetRs;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRq;
import ru.gold.ordance.board.web.api.category.CategoryUpdateRq;
import ru.gold.ordance.board.web.api.client.ClientSaveRq;
import ru.gold.ordance.board.web.api.locality.LocalityUpdateRq;
import ru.gold.ordance.board.web.api.region.RegionUpdateRq;
import ru.gold.ordance.board.web.api.street.StreetUpdateRq;
import ru.gold.ordance.board.web.api.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.web.service.base.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.generatePositiveInt;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
public class AdvertisementRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/advertisements/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private AdvertisementWebService service;

    @Autowired
    private LocalityWebService localityService;

    @Autowired
    private RegionWebService regionService;

    @Autowired
    private StreetWebService streetService;

    @Autowired
    private SubcategoryWebService subcategoryService;

    @Autowired
    private CategoryWebService categoryService;

    @Autowired
    private ClientWebService clientService;

    private Long savedLocalityId;

    private Long savedStreetId;

    private Long savedSubcategoryId;

    private Long savedClientId;

    private String categoryName;

    private String regionName;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        regionName = randomString();
        Long savedRegionId = regionService.update(RegionUpdateRq.builder()
                .name(regionName)
                .build())
                .getEntityId();

        savedLocalityId = localityService.update(LocalityUpdateRq.builder()
                .name(randomString())
                .regionId(savedRegionId)
                .build())
                .getEntityId();

        savedStreetId = streetService.update(StreetUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        categoryName = randomString();
        Long savedCategoryId = categoryService.update(CategoryUpdateRq.builder()
                .name(categoryName)
                .build())
                .getEntityId();

        savedSubcategoryId = subcategoryService.update(SubcategoryUpdateRq.builder()
                .name(randomString())
                .categoryId(savedCategoryId)
                .build())
                .getEntityId();

        savedClientId = clientService.save(ClientSaveRq.builder()
                .login(randomString())
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build())
                .getEntityId();
    }

    @Test
    public void findAll_noOneHasBeenFound() throws Exception {
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;

        final String savedName = randomString();
        final String savedDescription = randomString();
        final int savedPrice = generatePositiveInt();
        final String savedHouseNumber = randomString();

        final int savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                        .clientId(savedClientId)
                        .name(savedName)
                        .subcategoryId(savedSubcategoryId)
                        .description(savedDescription)
                        .price(savedPrice)
                        .localityId(savedLocalityId)
                        .streetId(savedStreetId)
                        .houseNumber(savedHouseNumber)
                        .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundOne)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", equalTo(savedAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", is(savedName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", is(savedDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", equalTo(savedPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", is(savedHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstName = randomString();
        final String firstDescription = randomString();
        final int firstPrice = generatePositiveInt();
        final String firstHouseNumber = randomString();

        final int firstAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(firstName)
                .subcategoryId(savedSubcategoryId)
                .description(firstDescription)
                .price(firstPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(firstHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        final String secondName = randomString();
        final String secondDescription = randomString();
        final int secondPrice = generatePositiveInt();
        final String secondHouseNumber = randomString();

        final int secondAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(secondName)
                .subcategoryId(savedSubcategoryId)
                .description(secondDescription)
                .price(secondPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(secondHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundALot)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", oneOf(firstAdvertisementId, secondAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", oneOf(firstName, secondName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", oneOf(firstDescription, secondDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", oneOf(firstPrice, secondPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", oneOf(firstHouseNumber, secondHouseNumber)))
                .andExpect(jsonPath("$.advertisementList[1].entityId", oneOf(firstAdvertisementId, secondAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[1].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[1].name", oneOf(firstName, secondName)))
                .andExpect(jsonPath("$.advertisementList[1].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[1].description", oneOf(firstDescription, secondDescription)))
                .andExpect(jsonPath("$.advertisementList[1].price", oneOf(firstPrice, secondPrice)))
                .andExpect(jsonPath("$.advertisementList[1].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[1].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[1].houseNumber", oneOf(firstHouseNumber, secondHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundALot)));
    }

    @Test
    public void findById_notFound() throws Exception {
        String fakeId = "999";
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + fakeId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;

        final String savedName = randomString();
        final String savedDescription = randomString();
        final int savedPrice = generatePositiveInt();
        final String savedHouseNumber = randomString();

        final int savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(savedName)
                .subcategoryId(savedSubcategoryId)
                .description(savedDescription)
                .price(savedPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(savedHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedAdvertisementId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundOne)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", equalTo(savedAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", is(savedName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", is(savedDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", equalTo(savedPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", is(savedHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByCategoryName_notFound() throws Exception {
        String fakeName = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "category-name/" + fakeName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByCategoryName_found() throws Exception {
        final int foundOne = 1;

        final String savedName = randomString();
        final String savedDescription = randomString();
        final int savedPrice = generatePositiveInt();
        final String savedHouseNumber = randomString();

        final int savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(savedName)
                .subcategoryId(savedSubcategoryId)
                .description(savedDescription)
                .price(savedPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(savedHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "category-name/" + categoryName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundOne)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", equalTo(savedAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", is(savedName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", is(savedDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", equalTo(savedPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", is(savedHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByRegionName_notFound() throws Exception {
        String fakeName = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "region-name/" + fakeName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByRegionName_found() throws Exception {
        final int foundOne = 1;

        final String savedName = randomString();
        final String savedDescription = randomString();
        final int savedPrice = generatePositiveInt();
        final String savedHouseNumber = randomString();

        final int savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(savedName)
                .subcategoryId(savedSubcategoryId)
                .description(savedDescription)
                .price(savedPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(savedHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "region-name/" + regionName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundOne)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", equalTo(savedAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", is(savedName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", is(savedDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", equalTo(savedPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", is(savedHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByCategoryNameAndRegionName_notFound() throws Exception {
        String fakeName = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "region-name/" + fakeName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByCategoryNameAndRegionName_found() throws Exception {
        final int foundOne = 1;

        final String savedName = randomString();
        final String savedDescription = randomString();
        final int savedPrice = generatePositiveInt();
        final String savedHouseNumber = randomString();

        final int savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(savedName)
                .subcategoryId(savedSubcategoryId)
                .description(savedDescription)
                .price(savedPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(savedHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "category-name/" + categoryName + "/region-name/" + regionName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundOne)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", equalTo(savedAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", is(savedName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", is(savedDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", equalTo(savedPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", is(savedHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByName_notFound() throws Exception {
        String fakeName = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "name/" + fakeName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;

        final String savedName = randomString();
        final String savedDescription = randomString();
        final int savedPrice = generatePositiveInt();
        final String savedHouseNumber = randomString();

        final int savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(savedName)
                .subcategoryId(savedSubcategoryId)
                .description(savedDescription)
                .price(savedPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(savedHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.advertisementList", hasSize(foundOne)))
                .andExpect(jsonPath("$.advertisementList[0].entityId", equalTo(savedAdvertisementId)))
                .andExpect(jsonPath("$.advertisementList[0].clientId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].name", is(savedName)))
                .andExpect(jsonPath("$.advertisementList[0].subcategoryId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].description", is(savedDescription)))
                .andExpect(jsonPath("$.advertisementList[0].price", equalTo(savedPrice)))
                .andExpect(jsonPath("$.advertisementList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.advertisementList[0].houseNumber", is(savedHouseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveAdvertisement() throws Exception {
        final boolean updated = true;

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_saveAdvertisement_clientDoesNotExistByCurrentId() throws Exception {
        Long currentId = 999L;
        final boolean updated = false;

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(currentId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_saveAdvertisement_subcategoryDoesNotExistByCurrentId() throws Exception {
        Long currentId = 999L;
        final boolean updated = false;

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(currentId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_saveAdvertisement_localityDoesNotExistByCurrentId() throws Exception {
        Long currentId = 999L;
        final boolean updated = false;

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(currentId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_saveAdvertisement_streetDoesNotExistByCurrentId() throws Exception {
        Long currentId = 999L;
        final boolean updated = false;

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(currentId)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_updateAdvertisement() throws Exception {
        final boolean updated = true;

        final Long savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build())
                .getEntityId();

        final String newName = randomString();
        final String newDescription = randomString();
        final int newPrice = generatePositiveInt();
        final String newHouseNumber = randomString();
        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .entityId(savedAdvertisementId)
                .clientId(savedClientId)
                .name(newName)
                .subcategoryId(savedSubcategoryId)
                .description(newDescription)
                .price(newPrice)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(newHouseNumber)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedAdvertisementId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        AdvertisementGetRs rs = service.findById(new AdvertisementGetByIdRq(savedAdvertisementId));

        assertEquals(1, rs.getAdvertisementList().size());
        assertEquals(newName, rs.getAdvertisementList().get(0).getName());
        assertEquals(newDescription, rs.getAdvertisementList().get(0).getDescription());
        assertEquals(newPrice, rs.getAdvertisementList().get(0).getPrice());
        assertEquals(newHouseNumber, rs.getAdvertisementList().get(0).getHouseNumber());
    }

    @Test
    public void update_updateAdvertisement_advertisementDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .entityId(currentId)
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void deleteById() throws Exception {
        final Long savedAdvertisementId = service.update(AdvertisementUpdateRq.builder()
                .clientId(savedClientId)
                .name(randomString())
                .subcategoryId(savedSubcategoryId)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedAdvertisementId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        AdvertisementGetRs rs = service.findById(new AdvertisementGetByIdRq(savedAdvertisementId));

        assertEquals(0, rs.getAdvertisementList().size());
    }
}

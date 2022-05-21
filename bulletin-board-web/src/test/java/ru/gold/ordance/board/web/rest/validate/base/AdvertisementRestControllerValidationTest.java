package ru.gold.ordance.board.web.rest.validate.base;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.board.web.Main;
import ru.gold.ordance.board.web.api.advertisement.AdvertisementUpdateRq;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.board.web.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class AdvertisementRestControllerValidationTest {
    private final static String ENDPOINT = "/api/v1/advertisements/";
    private final static String INVALID_RQ = "INVALID_RQ";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void findById_idIsNotPositive_invalidRq() throws Exception {
        final long entityId = -1L;
        final String errorMessage = "The entityId is not positive.";

        mockMvc.perform(get(ENDPOINT + entityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.advertisementList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByCategoryName_nameIsEmpty_invalidRq() throws Exception {
        final String name = " ";
        final String errorMessage = "The name is empty.";

        mockMvc.perform(get(ENDPOINT + "category-name/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.advertisementList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByRegionName_nameIsEmpty_invalidRq() throws Exception {
        final String name = " ";
        final String errorMessage = "The name is empty.";

        mockMvc.perform(get(ENDPOINT + "region-name/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.advertisementList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByCategoryNameAndRegionName_categoryNameIsEmpty_invalidRq() throws Exception {
        final String categoryName = " ";
        final String regionName = randomString();
        final String errorMessage = "The categoryName is empty.";

        mockMvc.perform(get(ENDPOINT + "category-name/" + categoryName + "/region-name/" + regionName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.advertisementList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByCategoryNameAndRegionName_regionNameIsEmpty_invalidRq() throws Exception {
        final String categoryName = randomString();
        final String regionName = " ";
        final String errorMessage = "The regionName is empty.";

        mockMvc.perform(get(ENDPOINT + "category-name/" + categoryName + "/region-name/" + regionName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.advertisementList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByName_nameIsEmpty_invalidRq() throws Exception {
        final String name = " ";
        final String errorMessage = "The name is empty.";

        mockMvc.perform(get(ENDPOINT + "name/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.advertisementList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void update_clientIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The clientId is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(null)
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_clientIdIsNotPositive_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The clientId is not positive.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(-generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_nameIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The name is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(null)
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_nameIsEmpty_invalidRq() throws Exception {
        final boolean updated = false;
        final String name = Strings.EMPTY;
        final String errorMessage = "The name is empty.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(name)
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_subcategoryIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The subcategoryId is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(null)
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_subcategoryIdIsNotPositive_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The subcategoryId is not positive.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(-generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_priceIsNegative_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The price is negative.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(-generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_localityIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The localityId is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(null)
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_localityIdIsNotPositive_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The localityId is not positive.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(-generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_streetIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The streetId is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(null)
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_streetIdIsNotPositive_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The streetId is not positive.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(-generateId())
                .houseNumber(randomString())
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_houseNumberIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The houseNumber is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(null)
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_houseNumberIsEmpty_invalidRq() throws Exception {
        final boolean updated = false;
        final String houseNumber = Strings.EMPTY;
        final String errorMessage = "The houseNumber is empty.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(houseNumber)
                .photoId(generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_photoIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The streetId is null.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(null)
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_photoIdIsNotPositive_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The streetId is not positive.";

        AdvertisementUpdateRq rq = AdvertisementUpdateRq.builder()
                .clientId(generateId())
                .name(randomString())
                .subcategoryId(generateId())
                .description(randomString())
                .price(generatePositiveInt())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(randomString())
                .photoId(-generateId())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void deleteById_idIsNotPositive_invalidRq() throws Exception {
        final long entityId = -1L;
        final String errorMessage = "The entityId is not positive.";

        mockMvc.perform(delete(ENDPOINT + entityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)));
    }
}

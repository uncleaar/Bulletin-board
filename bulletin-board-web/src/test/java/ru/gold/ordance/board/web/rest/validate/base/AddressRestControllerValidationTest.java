package ru.gold.ordance.board.web.rest.validate.base;

import org.apache.logging.log4j.util.Strings;
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
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.board.web.Main;
import ru.gold.ordance.board.web.api.address.AddressUpdateRq;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.board.web.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.generateId;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
@ActiveProfiles("test")
public class AddressRestControllerValidationTest {
    private final static String ENDPOINT = "/api/v1/addresses/";
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
                .andExpect(jsonPath("$.addressList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void update_localityIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The localityId is null.";

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(generateId())
                .localityId(null)
                .streetId(generateId())
                .houseNumber(randomString())
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

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(generateId())
                .localityId(-generateId())
                .streetId(generateId())
                .houseNumber(randomString())
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

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(generateId())
                .localityId(generateId())
                .streetId(null)
                .houseNumber(randomString())
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

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(generateId())
                .localityId(generateId())
                .streetId(-generateId())
                .houseNumber(randomString())
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

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(generateId())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(null)
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

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(generateId())
                .localityId(generateId())
                .streetId(generateId())
                .houseNumber(houseNumber)
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

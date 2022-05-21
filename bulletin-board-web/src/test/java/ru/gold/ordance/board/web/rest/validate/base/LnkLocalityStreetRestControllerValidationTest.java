package ru.gold.ordance.board.web.rest.validate.base;

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
import ru.gold.ordance.board.web.api.lnk.LnkLocalityStreetUpdateRq;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.web.utils.RequestUtils.JSON;
import static ru.gold.ordance.board.web.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.generateId;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
@ActiveProfiles("test")
@PropertySource("classpath:application-test.properties")
public class LnkLocalityStreetRestControllerValidationTest {
    private final static String ENDPOINT = "/api/v1/links-locality-street/";
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
                .andExpect(jsonPath("$.lnkLocalityStreets", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByLocality_localityIdIsNotPositive_invalidRq() throws Exception {
        final long localityId = -1L;
        final String errorMessage = "The localityId is not positive.";

        mockMvc.perform(get(ENDPOINT + "locality/" + localityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.lnkLocalityStreets", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByStreet_streetIdIsNotPositive_invalidRq() throws Exception {
        final long streetId = -1L;
        final String errorMessage = "The streetId is not positive.";

        mockMvc.perform(get(ENDPOINT + "street/" + streetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.lnkLocalityStreets", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByLocalityAndStreet_localityIdIsNotPositive_invalidRq() throws Exception {
        final long localityId = -1L;
        final long streetId = 1L;
        final String errorMessage = "The localityId is not positive.";

        mockMvc.perform(get(ENDPOINT + "locality/" + localityId + "/street/" + streetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.lnkLocalityStreets", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByLocalityAndStreet_streetIdIsNotPositive_invalidRq() throws Exception {
        final long localityId = 1L;
        final long streetId = -1L;
        final String errorMessage = "The streetId is not positive.";

        mockMvc.perform(get(ENDPOINT + "locality/" + localityId + "/street/" + streetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.lnkLocalityStreets", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void update_localityIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The localityId is null.";

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .entityId(generateId())
                .localityId(null)
                .streetId(generateId())
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

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .entityId(generateId())
                .localityId(-generateId())
                .streetId(generateId())
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

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .entityId(generateId())
                .localityId(generateId())
                .streetId(null)
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

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .entityId(generateId())
                .localityId(generateId())
                .streetId(-generateId())
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

package ru.gold.ordance.board.api.rest.heir.complex.validate;

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
import ru.gold.ordance.board.api.Main;
import ru.gold.ordance.board.model.api.domain.complex.ComplexAddressUpdateRq;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.api.utils.RequestUtils.JSON;
import static ru.gold.ordance.board.api.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
@ActiveProfiles("test")
public class ComplexAddressRestControllerValidationTest {
    private final static String ENDPOINT = "/api/v1/complex-address/";
    private final static String INVALID_RQ = "INVALID_RQ";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void update_regionNameIsEmpty_invalidRq() throws Exception {
        final String name = Strings.EMPTY;
        final String errorMessage = "The regionName is empty.";

        ComplexAddressUpdateRq rq = ComplexAddressUpdateRq.builder()
                .regionName(name)
                .localityName(randomString())
                .streetName(randomString())
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.regionId", nullValue()))
                .andExpect(jsonPath("$.localityId", nullValue()))
                .andExpect(jsonPath("$.streetId", nullValue()));
    }

    @Test
    public void update_localityNameIsEmpty_invalidRq() throws Exception {
        final String name = Strings.EMPTY;
        final String errorMessage = "The localityName is empty.";

        ComplexAddressUpdateRq rq = ComplexAddressUpdateRq.builder()
                .regionName(randomString())
                .localityName(name)
                .streetName(randomString())
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.regionId", nullValue()))
                .andExpect(jsonPath("$.localityId", nullValue()))
                .andExpect(jsonPath("$.streetId", nullValue()));
    }

    @Test
    public void update_streetNameIsEmpty_invalidRq() throws Exception {
        final String name = Strings.EMPTY;
        final String errorMessage = "The streetName is empty.";

        ComplexAddressUpdateRq rq = ComplexAddressUpdateRq.builder()
                .regionName(randomString())
                .localityName(randomString())
                .streetName(name)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.regionId", nullValue()))
                .andExpect(jsonPath("$.localityId", nullValue()))
                .andExpect(jsonPath("$.streetId", nullValue()));
    }

    @Test
    public void update_houseNumberIsEmpty_invalidRq() throws Exception {
        final String name = Strings.EMPTY;
        final String errorMessage = "The houseNumber is empty.";

        ComplexAddressUpdateRq rq = ComplexAddressUpdateRq.builder()
                .regionName(randomString())
                .localityName(randomString())
                .streetName(randomString())
                .houseNumber(name)
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.regionId", nullValue()))
                .andExpect(jsonPath("$.localityId", nullValue()))
                .andExpect(jsonPath("$.streetId", nullValue()));
    }
}

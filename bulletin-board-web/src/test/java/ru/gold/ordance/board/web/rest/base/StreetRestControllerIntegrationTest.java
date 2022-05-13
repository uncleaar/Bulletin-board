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
import ru.gold.ordance.board.web.api.street.StreetGetByIdRq;
import ru.gold.ordance.board.web.api.street.StreetGetRs;
import ru.gold.ordance.board.web.api.street.StreetUpdateRq;
import ru.gold.ordance.board.web.service.base.StreetWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
public class StreetRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/streets/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private StreetWebService service;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void findAll_noOneHasBeenFound() throws Exception {
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.streetList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;
        final String savedStreetName = randomString();

        final int savedStreetId = service.update(StreetUpdateRq.builder()
                .name(savedStreetName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.streetList", hasSize(foundOne)))
                .andExpect(jsonPath("$.streetList[0].entityId", equalTo(savedStreetId)))
                .andExpect(jsonPath("$.streetList[0].name", is(savedStreetName)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstSavedStreetName = randomString();
        final String secondSavedStreetName = randomString();

        final int firstSavedStreetId = service.update(StreetUpdateRq.builder()
                .name(firstSavedStreetName)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedStreetId = service.update(StreetUpdateRq.builder()
                .name(secondSavedStreetName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.streetList", hasSize(foundALot)))
                .andExpect(jsonPath("$.streetList[0].entityId", oneOf(firstSavedStreetId, secondSavedStreetId)))
                .andExpect(jsonPath("$.streetList[0].name", oneOf(firstSavedStreetName, secondSavedStreetName)))
                .andExpect(jsonPath("$.streetList[1].entityId", oneOf(firstSavedStreetId, secondSavedStreetId)))
                .andExpect(jsonPath("$.streetList[1].name", oneOf(firstSavedStreetName, secondSavedStreetName)))
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
                .andExpect(jsonPath("$.streetList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;
        final String savedStreetName = randomString();

        final int savedStreetId = service.update(StreetUpdateRq.builder()
                .name(savedStreetName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedStreetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.streetList", hasSize(foundOne)))
                .andExpect(jsonPath("$.streetList[0].entityId", equalTo(savedStreetId)))
                .andExpect(jsonPath("$.streetList[0].name", is(savedStreetName)))
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
                .andExpect(jsonPath("$.streetList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;
        final String savedStreetName = randomString();

        final int savedStreetId = service.update(StreetUpdateRq.builder()
                .name(savedStreetName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedStreetName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.streetList", hasSize(foundOne)))
                .andExpect(jsonPath("$.streetList[0].entityId", equalTo(savedStreetId)))
                .andExpect(jsonPath("$.streetList[0].name", is(savedStreetName)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveStreet() throws Exception {
        final boolean updated = true;

        StreetUpdateRq rq = StreetUpdateRq.builder()
                .name(randomString())
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
    public void update_saveStreet_streetNameAlreadyExists() throws Exception {
        final boolean updated = false;
        final String streetName = randomString();

        service.update(StreetUpdateRq.builder()
                .name(streetName)
                .build());

        StreetUpdateRq rq = StreetUpdateRq.builder()
                .name(streetName)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(VIOLATES_CONSTRAINT)))
                .andExpect(jsonPath("$.status.description", is(CONSTRAINT_MESSAGE)))
                .andExpect(jsonPath("$.entityId", nullValue()))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_updateStreet() throws Exception {
        final boolean updated = true;

        final Long savedStreetId = service.update(StreetUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        final String newStreetName = randomString();
        StreetUpdateRq rq = StreetUpdateRq.builder()
                .entityId(savedStreetId)
                .name(newStreetName)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        StreetGetRs rs = service.findById(new StreetGetByIdRq(savedStreetId));

        assertEquals(1, rs.getStreetList().size());
        assertEquals(newStreetName, rs.getStreetList().get(0).getName());
    }

    @Test
    public void update_updateStreet_streetDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        StreetUpdateRq rq = StreetUpdateRq.builder()
                .entityId(currentId)
                .name(randomString())
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
        final Long savedStreetId = service.update(StreetUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedStreetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        StreetGetRs rs = service.findById(new StreetGetByIdRq(savedStreetId));

        assertEquals(0, rs.getStreetList().size());
    }
}

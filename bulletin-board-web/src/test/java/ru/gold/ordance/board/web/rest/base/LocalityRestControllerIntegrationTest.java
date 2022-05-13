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
import ru.gold.ordance.board.web.api.locality.LocalityGetByIdRq;
import ru.gold.ordance.board.web.api.locality.LocalityGetRs;
import ru.gold.ordance.board.web.api.locality.LocalityUpdateRq;
import ru.gold.ordance.board.web.api.region.RegionUpdateRq;
import ru.gold.ordance.board.web.service.base.LocalityWebService;
import ru.gold.ordance.board.web.service.base.RegionWebService;

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
public class LocalityRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/localities/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private LocalityWebService service;

    @Autowired
    private RegionWebService regionService;

    private Long savedRegionId;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        savedRegionId = regionService.update(RegionUpdateRq.builder()
                .name(randomString())
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
                .andExpect(jsonPath("$.localityList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;
        final String savedLocalityName = randomString();

        final int savedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(savedLocalityName)
                .regionId(savedRegionId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.localityList", hasSize(foundOne)))
                .andExpect(jsonPath("$.localityList[0].entityId", equalTo(savedLocalityId)))
                .andExpect(jsonPath("$.localityList[0].name", is(savedLocalityName)))
                .andExpect(jsonPath("$.localityList[0].regionId", is(savedRegionId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstSavedLocalityName = randomString();
        final String secondSavedLocalityName = randomString();

        final int firstSavedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(firstSavedLocalityName)
                .regionId(savedRegionId)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(secondSavedLocalityName)
                .regionId(savedRegionId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.localityList", hasSize(foundALot)))
                .andExpect(jsonPath("$.localityList[0].entityId", oneOf(firstSavedLocalityId, secondSavedLocalityId)))
                .andExpect(jsonPath("$.localityList[0].name", oneOf(firstSavedLocalityName, secondSavedLocalityName)))
                .andExpect(jsonPath("$.localityList[0].regionId", is(savedRegionId.intValue())))
                .andExpect(jsonPath("$.localityList[1].entityId", oneOf(firstSavedLocalityId, secondSavedLocalityId)))
                .andExpect(jsonPath("$.localityList[1].name", oneOf(firstSavedLocalityName, secondSavedLocalityName)))
                .andExpect(jsonPath("$.localityList[1].regionId", is(savedRegionId.intValue())))
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
                .andExpect(jsonPath("$.localityList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;
        final String savedLocalityName = randomString();

        final int savedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(savedLocalityName)
                .regionId(savedRegionId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedLocalityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.localityList", hasSize(foundOne)))
                .andExpect(jsonPath("$.localityList[0].entityId", equalTo(savedLocalityId)))
                .andExpect(jsonPath("$.localityList[0].name", is(savedLocalityName)))
                .andExpect(jsonPath("$.localityList[0].regionId", is(savedRegionId.intValue())))
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
                .andExpect(jsonPath("$.localityList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;
        final String savedLocalityName = randomString();

        final int savedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(savedLocalityName)
                .regionId(savedRegionId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedLocalityName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.localityList", hasSize(foundOne)))
                .andExpect(jsonPath("$.localityList[0].entityId", equalTo(savedLocalityId)))
                .andExpect(jsonPath("$.localityList[0].name", is(savedLocalityName)))
                .andExpect(jsonPath("$.localityList[0].regionId", is(savedRegionId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveLocality() throws Exception {
        final boolean updated = true;

        LocalityUpdateRq rq = LocalityUpdateRq.builder()
                .name(randomString())
                .regionId(savedRegionId)
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
    public void update_saveLocality_localityNameAlreadyExists() throws Exception {
        final boolean updated = false;
        final String localityName = randomString();

        service.update(LocalityUpdateRq.builder()
                .name(localityName)
                .regionId(savedRegionId)
                .build());

        LocalityUpdateRq rq = LocalityUpdateRq.builder()
                .name(localityName)
                .regionId(savedRegionId)
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
    public void update_saveLocality_regionDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        LocalityUpdateRq rq = LocalityUpdateRq.builder()
                .name(randomString())
                .regionId(currentId)
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
    public void update_updateLocality() throws Exception {
        final boolean updated = true;

        final Long savedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(randomString())
                .regionId(savedRegionId)
                .build())
                .getEntityId();

        final String newLocalityName = randomString();
        LocalityUpdateRq rq = LocalityUpdateRq.builder()
                .entityId(savedLocalityId)
                .name(newLocalityName)
                .regionId(savedRegionId)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        LocalityGetRs rs = service.findById(new LocalityGetByIdRq(savedLocalityId));

        assertEquals(1, rs.getLocalityList().size());
        assertEquals(newLocalityName, rs.getLocalityList().get(0).getName());
    }

    @Test
    public void update_updateLocality_localityDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        LocalityUpdateRq rq = LocalityUpdateRq.builder()
                .entityId(currentId)
                .name(randomString())
                .regionId(savedRegionId)
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
        final Long savedLocalityId = service.update(LocalityUpdateRq.builder()
                .name(randomString())
                .regionId(savedRegionId)
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedLocalityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        LocalityGetRs rs = service.findById(new LocalityGetByIdRq(savedLocalityId));

        assertEquals(0, rs.getLocalityList().size());
    }
}

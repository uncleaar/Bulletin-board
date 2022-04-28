package ru.gold.ordance.board.api.rest.heir.base;

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
import ru.gold.ordance.board.api.Main;
import ru.gold.ordance.board.model.api.domain.region.RegionGetByIdRq;
import ru.gold.ordance.board.model.api.domain.region.RegionGetRs;
import ru.gold.ordance.board.model.api.domain.region.RegionUpdateRq;
import ru.gold.ordance.board.web.service.base.RegionWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.api.utils.RequestUtils.*;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
public class RegionRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/regions/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private RegionWebService service;

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
                .andExpect(jsonPath("$.regionList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;
        final String savedRegionName = randomString();

        final int savedRegionId = service.update(RegionUpdateRq.builder()
                .name(savedRegionName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.regionList", hasSize(foundOne)))
                .andExpect(jsonPath("$.regionList[0].entityId", equalTo(savedRegionId)))
                .andExpect(jsonPath("$.regionList[0].name", is(savedRegionName)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstSavedRegionName = randomString();
        final String secondSavedRegionName = randomString();

        final int firstSavedRegionId = service.update(RegionUpdateRq.builder()
                .name(firstSavedRegionName)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedRegionId = service.update(RegionUpdateRq.builder()
                .name(secondSavedRegionName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.regionList", hasSize(foundALot)))
                .andExpect(jsonPath("$.regionList[0].entityId", oneOf(firstSavedRegionId, secondSavedRegionId)))
                .andExpect(jsonPath("$.regionList[0].name", oneOf(firstSavedRegionName, secondSavedRegionName)))
                .andExpect(jsonPath("$.regionList[1].entityId", oneOf(firstSavedRegionId, secondSavedRegionId)))
                .andExpect(jsonPath("$.regionList[1].name", oneOf(firstSavedRegionName, secondSavedRegionName)))
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
                .andExpect(jsonPath("$.regionList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;
        final String savedRegionName = randomString();

        final int savedRegionId = service.update(RegionUpdateRq.builder()
                .name(savedRegionName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedRegionId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.regionList", hasSize(foundOne)))
                .andExpect(jsonPath("$.regionList[0].entityId", equalTo(savedRegionId)))
                .andExpect(jsonPath("$.regionList[0].name", is(savedRegionName)))
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
                .andExpect(jsonPath("$.regionList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;
        final String savedRegionName = randomString();

        final int savedRegionId = service.update(RegionUpdateRq.builder()
                .name(savedRegionName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedRegionName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.regionList", hasSize(foundOne)))
                .andExpect(jsonPath("$.regionList[0].entityId", equalTo(savedRegionId)))
                .andExpect(jsonPath("$.regionList[0].name", is(savedRegionName)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveRegion() throws Exception {
        final boolean updated = true;

        RegionUpdateRq rq = RegionUpdateRq.builder()
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
    public void update_saveRegion_regionNameAlreadyExists() throws Exception {
        final boolean updated = false;
        final String regionName = randomString();

        service.update(RegionUpdateRq.builder()
                .name(regionName)
                .build());

        RegionUpdateRq rq = RegionUpdateRq.builder()
                .name(regionName)
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
    public void update_updateRegion() throws Exception {
        final boolean updated = true;

        final Long savedRegionId = service.update(RegionUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        final String newRegionName = randomString();
        RegionUpdateRq rq = RegionUpdateRq.builder()
                .entityId(savedRegionId)
                .name(newRegionName)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedRegionId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        RegionGetRs rs = service.findById(new RegionGetByIdRq(savedRegionId));

        assertEquals(1, rs.getRegionList().size());
        assertEquals(newRegionName, rs.getRegionList().get(0).getName());
    }

    @Test
    public void update_updateRegion_regionDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        RegionUpdateRq rq = RegionUpdateRq.builder()
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
        final Long savedRegionId = service.update(RegionUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedRegionId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        RegionGetRs rs = service.findById(new RegionGetByIdRq(savedRegionId));

        assertEquals(0, rs.getRegionList().size());
    }
}



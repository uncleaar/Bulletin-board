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
import ru.gold.ordance.board.web.api.lnk.LnkLocalityStreetGetByIdRq;
import ru.gold.ordance.board.web.api.lnk.LnkLocalityStreetGetRs;
import ru.gold.ordance.board.web.api.lnk.LnkLocalityStreetUpdateRq;
import ru.gold.ordance.board.web.api.locality.LocalityUpdateRq;
import ru.gold.ordance.board.web.api.region.RegionUpdateRq;
import ru.gold.ordance.board.web.api.street.StreetUpdateRq;
import ru.gold.ordance.board.web.service.base.LnkLocalityStreetWebService;
import ru.gold.ordance.board.web.service.base.LocalityWebService;
import ru.gold.ordance.board.web.service.base.RegionWebService;
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
public class LnkLocalityStreetRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/links-locality-street/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private LnkLocalityStreetWebService service;

    @Autowired
    private LocalityWebService localityService;

    @Autowired
    private RegionWebService regionService;

    @Autowired
    private StreetWebService streetService;

    private Long savedLocalityId;

    private Long savedStreetId;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        Long savedRegionId = regionService.update(RegionUpdateRq.builder()
                .name(randomString())
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
    }

    @Test
    public void findAll_noOneHasBeenFound() throws Exception {
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;

        final int savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundOne)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", equalTo(savedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final Long otherSavedStreetId = streetService.update(StreetUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        final int firstSavedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(otherSavedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundALot)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", oneOf(firstSavedLnkId, secondSavedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", oneOf(savedStreetId.intValue(), otherSavedStreetId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].entityId", oneOf(firstSavedLnkId, secondSavedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].streetId", oneOf(savedStreetId.intValue(), otherSavedStreetId.intValue())))
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
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;

        final int savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedLnkId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundOne)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", equalTo(savedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByLocalityAndStreet_notFound() throws Exception {
        String fakeId = "999";
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "locality/" + fakeId + "/street/" + fakeId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByLocalityAndStreet_found() throws Exception {
        final int foundOne = 1;

        final int savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "locality/" + savedLocalityId + "/street/" + savedStreetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundOne)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", equalTo(savedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByLocality_notFound() throws Exception {
        String fakeId = "999";
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "locality/" + fakeId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByLocality_foundOne() throws Exception {
        final int foundOne = 1;

        final int savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "locality/" + savedLocalityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundOne)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", equalTo(savedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByLocality_foundALot() throws Exception {
        final int foundALot = 2;

        final Long otherSavedStreetId = streetService.update(StreetUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        final int firstSavedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(otherSavedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "locality/" + savedLocalityId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundALot)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", oneOf(firstSavedLnkId, secondSavedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", oneOf(savedStreetId.intValue(), otherSavedStreetId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].entityId", oneOf(firstSavedLnkId, secondSavedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].streetId", oneOf(savedStreetId.intValue(), otherSavedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundALot)));
    }

    @Test
    public void findByStreet_notFound() throws Exception {
        String fakeId = "999";
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "street/" + fakeId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByStreet_foundOne() throws Exception {
        final int foundOne = 1;

        final int savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "street/" + savedStreetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundOne)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", equalTo(savedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByStreet_foundALot() throws Exception {
        final int foundALot = 2;

        final Long savedRegionId = regionService.update(RegionUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        final Long otherSavedLocalityId = localityService.update(LocalityUpdateRq.builder()
                .name(randomString())
                .regionId(savedRegionId)
                .build())
                .getEntityId();

        final int firstSavedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(otherSavedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "street/" + savedStreetId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.lnkLocalityStreets", hasSize(foundALot)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].entityId", oneOf(firstSavedLnkId, secondSavedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].localityId", oneOf(savedLocalityId.intValue(), otherSavedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].entityId", oneOf(firstSavedLnkId, secondSavedLnkId)))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].localityId", oneOf(savedLocalityId.intValue(), otherSavedLocalityId.intValue())))
                .andExpect(jsonPath("$.lnkLocalityStreets[1].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.total", is(foundALot)));
    }

    @Test
    public void update_saveLnk() throws Exception {
        final boolean updated = true;

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
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
    public void update_saveLnk_lnkAlreadyExists() throws Exception {
        final boolean updated = false;

        service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build());

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
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
    public void update_updateLnk() throws Exception {
        final boolean updated = true;

        final Long savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId();

        final Long newSavedStreetId = streetService.update(StreetUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();
        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .entityId(savedLnkId)
                .localityId(savedLocalityId)
                .streetId(newSavedStreetId)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedLnkId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        LnkLocalityStreetGetRs rs = service.findById(new LnkLocalityStreetGetByIdRq(savedLnkId));

        assertEquals(1, rs.getLnkLocalityStreets().size());
        assertEquals(newSavedStreetId, rs.getLnkLocalityStreets().get(0).getStreetId());
    }

    @Test
    public void update_updateLnk_lnkDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        LnkLocalityStreetUpdateRq rq = LnkLocalityStreetUpdateRq.builder()
                .entityId(currentId)
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
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
        final Long savedLnkId = service.update(LnkLocalityStreetUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedLnkId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        LnkLocalityStreetGetRs rs = service.findById(new LnkLocalityStreetGetByIdRq(savedLnkId));

        assertEquals(0, rs.getLnkLocalityStreets().size());
    }
}

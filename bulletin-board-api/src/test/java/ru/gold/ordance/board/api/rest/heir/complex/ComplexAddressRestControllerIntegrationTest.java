package ru.gold.ordance.board.api.rest.heir.complex;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.board.api.Main;
import ru.gold.ordance.board.model.api.domain.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.model.api.domain.locality.LocalityGetByNameRq;
import ru.gold.ordance.board.model.api.domain.locality.LocalityGetRs;
import ru.gold.ordance.board.model.api.domain.locality.LocalityUpdateRq;
import ru.gold.ordance.board.model.api.domain.region.RegionGetByNameRq;
import ru.gold.ordance.board.model.api.domain.region.RegionGetRs;
import ru.gold.ordance.board.model.api.domain.region.RegionUpdateRq;
import ru.gold.ordance.board.model.api.domain.street.StreetGetByNameRq;
import ru.gold.ordance.board.model.api.domain.street.StreetGetRs;
import ru.gold.ordance.board.model.api.domain.street.StreetUpdateRq;
import ru.gold.ordance.board.web.service.base.LocalityWebService;
import ru.gold.ordance.board.web.service.base.RegionWebService;
import ru.gold.ordance.board.web.service.base.StreetWebService;
import ru.gold.ordance.board.web.service.complex.ComplexAddressWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class ComplexAddressRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/complex-address/";
    private final static String SUCCESS = "SUCCESS";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ComplexAddressWebService service;

    @Autowired
    private RegionWebService regionService;

    @Autowired
    private LocalityWebService localityService;

    @Autowired
    private StreetWebService streetService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void update_nothingExists() throws Exception {
        final String regionName = randomString();
        final String localityName = randomString();
        final String streetName = randomString();
        final int found = 1;

        final ComplexAddressUpdateRq rq = ComplexAddressUpdateRq.builder()
                .regionName(regionName)
                .localityName(localityName)
                .streetName(streetName)
                .houseNumber(randomString())
                .build();

        ResultActions rs = mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        RegionGetRs regionRs = regionService.findByName(new RegionGetByNameRq(regionName));
        LocalityGetRs localityRs = localityService.findByName(new LocalityGetByNameRq(localityName));
        StreetGetRs streetRs = streetService.findByName(new StreetGetByNameRq(streetName));

        assertEquals(found, regionRs.getRegionList().size());
        assertEquals(found, localityRs.getLocalityList().size());
        assertEquals(found, streetRs.getStreetList().size());

        rs.andExpect(jsonPath("$.regionId", equalTo(regionRs.getRegionList().get(0).getEntityId().intValue())))
                .andExpect(jsonPath("$.localityId", equalTo(localityRs.getLocalityList().get(0).getEntityId().intValue())))
                .andExpect(jsonPath("$.streetId", equalTo(streetRs.getStreetList().get(0).getEntityId().intValue())));

        assertEquals(regionName, regionRs.getRegionList().get(0).getName());
        assertEquals(localityName, localityRs.getLocalityList().get(0).getName());
        assertEquals(streetName, streetRs.getStreetList().get(0).getName());
    }

    @Test
    public void update_everyoneExist() throws Exception {
        final String regionName = randomString();
        final String localityName = randomString();
        final String streetName = randomString();

        Long savedRegionId = regionService.update(RegionUpdateRq.builder()
                .name(regionName)
                .build()).getEntityId();

        Long savedLocalityId = localityService.update(LocalityUpdateRq.builder()
                .name(localityName)
                .regionId(savedRegionId)
                .build()).getEntityId();

        Long savedStreetId = streetService.update(StreetUpdateRq.builder()
                .name(streetName)
                .build()).getEntityId();

        final ComplexAddressUpdateRq rq = ComplexAddressUpdateRq.builder()
                .regionName(regionName)
                .localityName(localityName)
                .streetName(streetName)
                .houseNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.regionId", equalTo(savedRegionId.intValue())))
                .andExpect(jsonPath("$.localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.streetId", equalTo(savedStreetId.intValue())));

    }
}

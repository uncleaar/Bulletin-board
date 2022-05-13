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
import ru.gold.ordance.board.web.api.address.AddressGetByIdRq;
import ru.gold.ordance.board.web.api.address.AddressGetRs;
import ru.gold.ordance.board.web.api.address.AddressUpdateRq;
import ru.gold.ordance.board.web.api.locality.LocalityUpdateRq;
import ru.gold.ordance.board.web.api.region.RegionUpdateRq;
import ru.gold.ordance.board.web.api.street.StreetUpdateRq;
import ru.gold.ordance.board.web.service.base.AddressWebService;
import ru.gold.ordance.board.web.service.base.LocalityWebService;
import ru.gold.ordance.board.web.service.base.RegionWebService;
import ru.gold.ordance.board.web.service.base.StreetWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class AddressRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/addresses/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private AddressWebService service;

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
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;
        final String houseNumber = randomString();

        final int savedAddressId = service.update(AddressUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(houseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.addressList", hasSize(foundOne)))
                .andExpect(jsonPath("$.addressList[0].entityId", equalTo(savedAddressId)))
                .andExpect(jsonPath("$.addressList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.addressList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.addressList[0].houseNumber", is(houseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstHouseNumber = randomString();
        final String secondHouseNumber = randomString();

        final int firstSavedAddressId = service.update(AddressUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(firstHouseNumber)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedAddressId = service.update(AddressUpdateRq.builder()
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
                .andExpect(jsonPath("$.addressList", hasSize(foundALot)))
                .andExpect(jsonPath("$.addressList[0].entityId", oneOf(firstSavedAddressId, secondSavedAddressId)))
                .andExpect(jsonPath("$.addressList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.addressList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.addressList[0].houseNumber", oneOf(firstHouseNumber, secondHouseNumber)))
                .andExpect(jsonPath("$.addressList[1].entityId",  oneOf(firstSavedAddressId, secondSavedAddressId)))
                .andExpect(jsonPath("$.addressList[1].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.addressList[1].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.addressList[1].houseNumber", oneOf(firstHouseNumber, secondHouseNumber)))
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
                .andExpect(jsonPath("$.addressList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;
        final String houseNumber = randomString();

        final int savedAddressId = service.update(AddressUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(houseNumber)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedAddressId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.addressList", hasSize(foundOne)))
                .andExpect(jsonPath("$.addressList[0].entityId", equalTo(savedAddressId)))
                .andExpect(jsonPath("$.addressList[0].localityId", equalTo(savedLocalityId.intValue())))
                .andExpect(jsonPath("$.addressList[0].streetId", equalTo(savedStreetId.intValue())))
                .andExpect(jsonPath("$.addressList[0].houseNumber", is(houseNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveAddress() throws Exception {
        final boolean updated = true;

        AddressUpdateRq rq = AddressUpdateRq.builder()
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
    public void update_saveAddress_addressAlreadyExists() throws Exception {
        final boolean updated = false;
        final String houseNumber = randomString();

        service.update(AddressUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(houseNumber)
                .build());

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(houseNumber)
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
    public void update_updateAddress() throws Exception {
        final boolean updated = true;

        final Long savedAddressId = service.update(AddressUpdateRq.builder()
                .localityId(savedLocalityId)
                .streetId(savedStreetId)
                .houseNumber(randomString())
                .build())
                .getEntityId();

        final String newHouseNumber = randomString();
        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(savedAddressId)
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
                .andExpect(jsonPath("$.entityId", equalTo(savedAddressId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        AddressGetRs rs = service.findById(new AddressGetByIdRq(savedAddressId));

        assertEquals(1, rs.getAddressList().size());
        assertEquals(newHouseNumber, rs.getAddressList().get(0).getHouseNumber());
    }

    @Test
    public void update_updateAddress_addressDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        AddressUpdateRq rq = AddressUpdateRq.builder()
                .entityId(currentId)
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
}

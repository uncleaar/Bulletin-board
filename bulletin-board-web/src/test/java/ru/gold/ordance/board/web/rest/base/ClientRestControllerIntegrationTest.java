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
import ru.gold.ordance.board.web.api.client.ClientGetByIdRq;
import ru.gold.ordance.board.web.api.client.ClientGetRs;
import ru.gold.ordance.board.web.api.client.ClientSaveRq;
import ru.gold.ordance.board.web.api.client.ClientUpdateRq;
import ru.gold.ordance.board.web.service.base.ClientWebService;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@Transactional
@WebAppConfiguration
@ActiveProfiles("test")
public class ClientRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/clients/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ClientWebService service;

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
                .andExpect(jsonPath("$.clientList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;

        final String savedClientLogin = randomString();
        final String savedClientPassword = randomString();
        final String savedClientName = randomString();
        final String savedClientPhoneNumber = randomString();

        final int savedClientId = service.save(ClientSaveRq.builder()
                        .login(savedClientLogin)
                        .password(savedClientPassword)
                        .name(savedClientName)
                        .phoneNumber(savedClientPhoneNumber)
                        .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.clientList", hasSize(foundOne)))
                .andExpect(jsonPath("$.clientList[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.clientList[0].login", is(savedClientLogin)))
                .andExpect(jsonPath("$.clientList[0].name", is(savedClientName)))
                .andExpect(jsonPath("$.clientList[0].phoneNumber", is(savedClientPhoneNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstLogin = randomString();
        final String firstPassword = randomString();
        final String firstName = randomString();
        final String firstPhoneNumber = randomString();

        final int firstSavedClientId = service.save(ClientSaveRq.builder()
                        .login(firstLogin)
                        .password(firstPassword)
                        .name(firstName)
                        .phoneNumber(firstPhoneNumber)
                        .build())
                .getEntityId()
                .intValue();

        final String secondLogin = randomString();
        final String secondPassword = randomString();
        final String secondName = randomString();
        final String secondPhoneNumber = randomString();

        final int secondSavedClientId = service.save(ClientSaveRq.builder()
                        .login(secondLogin)
                        .password(secondPassword)
                        .name(secondName)
                        .phoneNumber(secondPhoneNumber)
                        .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.clientList", hasSize(foundALot)))
                .andExpect(jsonPath("$.clientList[0].entityId", oneOf(firstSavedClientId, secondSavedClientId)))
                .andExpect(jsonPath("$.clientList[0].login", oneOf(firstLogin, secondLogin)))
                .andExpect(jsonPath("$.clientList[0].name", oneOf(firstName, secondName)))
                .andExpect(jsonPath("$.clientList[0].phoneNumber", oneOf(firstPhoneNumber, secondPhoneNumber)))
                .andExpect(jsonPath("$.clientList[1].entityId", oneOf(firstSavedClientId, secondSavedClientId)))
                .andExpect(jsonPath("$.clientList[1].login", oneOf(firstLogin, secondLogin)))
                .andExpect(jsonPath("$.clientList[1].name", oneOf(firstName, secondName)))
                .andExpect(jsonPath("$.clientList[1].phoneNumber", oneOf(firstPhoneNumber, secondPhoneNumber)))
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
                .andExpect(jsonPath("$.clientList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;

        final String savedClientLogin = randomString();
        final String savedClientPassword = randomString();
        final String savedClientName = randomString();
        final String savedClientPhoneNumber = randomString();

        final int savedClientId = service.save(ClientSaveRq.builder()
                        .login(savedClientLogin)
                        .password(savedClientPassword)
                        .name(savedClientName)
                        .phoneNumber(savedClientPhoneNumber)
                        .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedClientId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.clientList", hasSize(foundOne)))
                .andExpect(jsonPath("$.clientList[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.clientList[0].login", is(savedClientLogin)))
                .andExpect(jsonPath("$.clientList[0].name", is(savedClientName)))
                .andExpect(jsonPath("$.clientList[0].phoneNumber", is(savedClientPhoneNumber)))
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
                .andExpect(jsonPath("$.clientList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;

        final String savedClientLogin = randomString();
        final String savedClientPassword = randomString();
        final String savedClientName = randomString();
        final String savedClientPhoneNumber = randomString();

        final int savedClientId = service.save(ClientSaveRq.builder()
                        .login(savedClientLogin)
                        .password(savedClientPassword)
                        .name(savedClientName)
                        .phoneNumber(savedClientPhoneNumber)
                        .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedClientName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.clientList", hasSize(foundOne)))
                .andExpect(jsonPath("$.clientList[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.clientList[0].login", is(savedClientLogin)))
                .andExpect(jsonPath("$.clientList[0].name", is(savedClientName)))
                .andExpect(jsonPath("$.clientList[0].phoneNumber", is(savedClientPhoneNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findByLogin_notFound() throws Exception {
        String fakeLogin = randomString();
        final int noOneHasBeenFound = 0;

        mockMvc.perform(get(ENDPOINT + "name/" + fakeLogin))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.clientList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByLogin_found() throws Exception {
        final int foundOne = 1;

        final String savedClientLogin = randomString();
        final String savedClientPassword = randomString();
        final String savedClientName = randomString();
        final String savedClientPhoneNumber = randomString();

        final int savedClientId = service.save(ClientSaveRq.builder()
                        .login(savedClientLogin)
                        .password(savedClientPassword)
                        .name(savedClientName)
                        .phoneNumber(savedClientPhoneNumber)
                        .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "login/" + savedClientLogin))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.clientList", hasSize(foundOne)))
                .andExpect(jsonPath("$.clientList[0].entityId", equalTo(savedClientId)))
                .andExpect(jsonPath("$.clientList[0].login", is(savedClientLogin)))
                .andExpect(jsonPath("$.clientList[0].name", is(savedClientName)))
                .andExpect(jsonPath("$.clientList[0].phoneNumber", is(savedClientPhoneNumber)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void save() throws Exception {
        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));
    }

    @Test
    public void save_loginAlreadyExists() throws Exception {
        final String login = randomString();

        service.save(ClientSaveRq.builder()
                .login(login)
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build());

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(login)
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(VIOLATES_CONSTRAINT)))
                .andExpect(jsonPath("$.status.description", is(CONSTRAINT_MESSAGE)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void update() throws Exception {
        final boolean updated = true;

        final Long savedClientId = service.save(ClientSaveRq.builder()
                        .login(randomString())
                        .password(randomString())
                        .name(randomString())
                        .phoneNumber(randomString())
                        .build())
                .getEntityId();

        final String name = randomString();
        final String phoneNumber = randomString();
        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(savedClientId)
                .password(randomString())
                .name(name)
                .phoneNumber(phoneNumber)
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedClientId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        ClientGetRs rs = service.findById(new ClientGetByIdRq(savedClientId));

        assertEquals(1, rs.getClientList().size());
        assertEquals(name, rs.getClientList().get(0).getName());
        assertEquals(phoneNumber, rs.getClientList().get(0).getPhoneNumber());
    }

    @Test
    public void update_clientDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(currentId)
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
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
        final Long savedClientId = service.save(ClientSaveRq.builder()
                        .login(randomString())
                        .password(randomString())
                        .name(randomString())
                        .phoneNumber(randomString())
                        .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedClientId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        ClientGetRs rs = service.findById(new ClientGetByIdRq(savedClientId));

        assertEquals(0, rs.getClientList().size());
    }
}

package ru.gold.ordance.board.api.rest.heir.base.validate;

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
import ru.gold.ordance.board.model.api.domain.client.ClientSaveRq;
import ru.gold.ordance.board.model.api.domain.client.ClientUpdateRq;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.gold.ordance.board.api.utils.RequestUtils.JSON;
import static ru.gold.ordance.board.api.utils.RequestUtils.toJSON;
import static ru.gold.ordance.board.common.utils.TestUtils.generateId;
import static ru.gold.ordance.board.common.utils.TestUtils.randomString;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Main.class })
@AutoConfigureTestDatabase
@WebAppConfiguration
@ActiveProfiles("test")
public class ClientRestControllerValidationTest {
    private final static String ENDPOINT = "/api/v1/clients/";
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
                .andExpect(jsonPath("$.clientList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByName_nameIsEmpty_invalidRq() throws Exception {
        final String name = " ";
        final String errorMessage = "The name is empty.";

        mockMvc.perform(get(ENDPOINT + "name/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.clientList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void findByLogin_loginIsEmpty_invalidRq() throws Exception {
        final String name = " ";
        final String errorMessage = "The login is empty.";

        mockMvc.perform(get(ENDPOINT + "login/" + name))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.clientList", nullValue()))
                .andExpect(jsonPath("$.total", nullValue()));
    }

    @Test
    public void save_loginIsNull_invalidRq() throws Exception {
        final String errorMessage = "The login is null.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(null)
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_loginIsEmpty_invalidRq() throws Exception {
        final String login = Strings.EMPTY;
        final String errorMessage = "The login is empty.";

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
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_passwordIsNull_invalidRq() throws Exception {
        final String errorMessage = "The password is null.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(null)
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_passwordIsEmpty_invalidRq() throws Exception {
        final String password = Strings.EMPTY;
        final String errorMessage = "The password is empty.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(password)
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_nameIsNull_invalidRq() throws Exception {
        final String errorMessage = "The name is null.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(randomString())
                .name(null)
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_nameIsEmpty_invalidRq() throws Exception {
        final String name = Strings.EMPTY;
        final String errorMessage = "The name is empty.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(randomString())
                .name(name)
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_phoneNumberIsNull_invalidRq() throws Exception {
        final String errorMessage = "The phoneNumber is null.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(randomString())
                .name(randomString())
                .phoneNumber(null)
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void save_phoneNumberIsEmpty_invalidRq() throws Exception {
        final String phoneNumber = Strings.EMPTY;
        final String errorMessage = "The phoneNumber is empty.";

        ClientSaveRq rq = ClientSaveRq.builder()
                .login(randomString())
                .password(randomString())
                .name(randomString())
                .phoneNumber(phoneNumber)
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.entityId", nullValue()));
    }

    @Test
    public void update_entityIdIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The entityId is null.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(null)
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_entityIdIsNotPositive_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The entityId is not positive.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(-generateId())
                .password(randomString())
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_passwordIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The password is null.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(generateId())
                .password(null)
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_passwordIsEmpty_invalidRq() throws Exception {
        final boolean updated = false;
        final String password = Strings.EMPTY;
        final String errorMessage = "The password is empty.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(generateId())
                .password(password)
                .name(randomString())
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_nameIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The name is null.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(generateId())
                .password(randomString())
                .name(null)
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_nameIsEmpty_invalidRq() throws Exception {
        final boolean updated = false;
        final String name = Strings.EMPTY;
        final String errorMessage = "The name is empty.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(generateId())
                .password(randomString())
                .name(name)
                .phoneNumber(randomString())
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_phoneNumberIsNull_invalidRq() throws Exception {
        final boolean updated = false;
        final String errorMessage = "The phoneNumber is null.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(generateId())
                .password(randomString())
                .name(randomString())
                .phoneNumber(null)
                .build();

        mockMvc.perform(put(ENDPOINT)
                        .content(toJSON(rq))
                        .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(INVALID_RQ)))
                .andExpect(jsonPath("$.status.description", equalTo(errorMessage)))
                .andExpect(jsonPath("$.updated", is(updated)));
    }

    @Test
    public void update_phoneNumberIsEmpty_invalidRq() throws Exception {
        final boolean updated = false;
        final String phoneNumber = Strings.EMPTY;
        final String errorMessage = "The phoneNumber is empty.";

        ClientUpdateRq rq = ClientUpdateRq.builder()
                .entityId(generateId())
                .password(randomString())
                .name(randomString())
                .phoneNumber(phoneNumber)
                .build();

        mockMvc.perform(put(ENDPOINT)
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

package ru.gold.ordance.board.web.rest.base;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.gold.ordance.board.web.Main;
import ru.gold.ordance.board.web.api.category.CategoryGetByIdRq;
import ru.gold.ordance.board.web.api.category.CategoryGetRs;
import ru.gold.ordance.board.web.api.category.CategoryUpdateRq;
import ru.gold.ordance.board.web.service.base.CategoryWebService;

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
@PropertySource("classpath:application-test.properties")
public class CategoryRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/categories/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private CategoryWebService service;

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
                .andExpect(jsonPath("$.categoryList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;
        final String savedCategoryName = randomString();

        final int savedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(savedCategoryName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.categoryList", hasSize(foundOne)))
                .andExpect(jsonPath("$.categoryList[0].entityId", equalTo(savedCategoryId)))
                .andExpect(jsonPath("$.categoryList[0].name", is(savedCategoryName)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstSavedCategoryName = randomString();
        final String secondSavedCategoryName = randomString();

        final int firstSavedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(firstSavedCategoryName)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(secondSavedCategoryName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.categoryList", hasSize(foundALot)))
                .andExpect(jsonPath("$.categoryList[0].entityId", oneOf(firstSavedCategoryId, secondSavedCategoryId)))
                .andExpect(jsonPath("$.categoryList[0].name", oneOf(firstSavedCategoryName, secondSavedCategoryName)))
                .andExpect(jsonPath("$.categoryList[1].entityId", oneOf(firstSavedCategoryId, secondSavedCategoryId)))
                .andExpect(jsonPath("$.categoryList[1].name", oneOf(firstSavedCategoryName, secondSavedCategoryName)))
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
                .andExpect(jsonPath("$.categoryList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;
        final String savedCategoryName = randomString();

        final int savedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(savedCategoryName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedCategoryId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.categoryList", hasSize(foundOne)))
                .andExpect(jsonPath("$.categoryList[0].entityId", equalTo(savedCategoryId)))
                .andExpect(jsonPath("$.categoryList[0].name", is(savedCategoryName)))
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
                .andExpect(jsonPath("$.categoryList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;
        final String savedCategoryName = randomString();

        final int savedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(savedCategoryName)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedCategoryName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.categoryList", hasSize(foundOne)))
                .andExpect(jsonPath("$.categoryList[0].entityId", equalTo(savedCategoryId)))
                .andExpect(jsonPath("$.categoryList[0].name", is(savedCategoryName)))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveCategory() throws Exception {
        final boolean updated = true;

        CategoryUpdateRq rq = CategoryUpdateRq.builder()
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
    public void update_saveCategory_categoryNameAlreadyExists() throws Exception {
        final boolean updated = false;
        final String categoryName = randomString();

        service.update(CategoryUpdateRq.builder()
                .name(categoryName)
                .build());

        CategoryUpdateRq rq = CategoryUpdateRq.builder()
                .name(categoryName)
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
    public void update_updateCategory() throws Exception {
        final boolean updated = true;

        final Long savedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        final String newCategoryName = randomString();
        CategoryUpdateRq rq = CategoryUpdateRq.builder()
                .entityId(savedCategoryId)
                .name(newCategoryName)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedCategoryId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        CategoryGetRs rs = service.findById(new CategoryGetByIdRq(savedCategoryId));

        assertEquals(1, rs.getCategoryList().size());
        assertEquals(newCategoryName, rs.getCategoryList().get(0).getName());
    }

    @Test
    public void update_updateCategory_CategoryDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        CategoryUpdateRq rq = CategoryUpdateRq.builder()
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
        final Long savedCategoryId = service.update(CategoryUpdateRq.builder()
                .name(randomString())
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedCategoryId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        CategoryGetRs rs = service.findById(new CategoryGetByIdRq(savedCategoryId));

        assertEquals(0, rs.getCategoryList().size());
    }
}

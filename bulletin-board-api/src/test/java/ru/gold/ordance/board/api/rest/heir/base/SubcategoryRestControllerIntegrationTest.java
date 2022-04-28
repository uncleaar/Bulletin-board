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
import ru.gold.ordance.board.model.api.domain.category.CategoryUpdateRq;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryGetByIdRq;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryGetRs;
import ru.gold.ordance.board.model.api.domain.subcategory.SubcategoryUpdateRq;
import ru.gold.ordance.board.web.service.base.CategoryWebService;
import ru.gold.ordance.board.web.service.base.SubcategoryWebService;

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
public class SubcategoryRestControllerIntegrationTest {
    private final static String ENDPOINT = "/api/v1/subcategories/";
    private final static String SUCCESS = "SUCCESS";
    private final static String VIOLATES_CONSTRAINT = "VIOLATES_CONSTRAINT";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private SubcategoryWebService service;

    @Autowired
    private CategoryWebService categoryService;

    private Long savedCategoryId;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        savedCategoryId = categoryService.update(CategoryUpdateRq.builder()
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
                .andExpect(jsonPath("$.subcategoryList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findAll_foundOne() throws Exception {
        final int foundOne = 1;
        final String savedSubcategoryName = randomString();

        final int savedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(savedSubcategoryName)
                .categoryId(savedCategoryId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.subcategoryList", hasSize(foundOne)))
                .andExpect(jsonPath("$.subcategoryList[0].entityId", equalTo(savedSubcategoryId)))
                .andExpect(jsonPath("$.subcategoryList[0].name", is(savedSubcategoryName)))
                .andExpect(jsonPath("$.subcategoryList[0].categoryId", is(savedCategoryId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void findAll_foundALot() throws Exception {
        final int foundALot = 2;

        final String firstSavedSubcategoryName = randomString();
        final String secondSavedSubcategoryName = randomString();

        final int firstSavedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(firstSavedSubcategoryName)
                .categoryId(savedCategoryId)
                .build())
                .getEntityId()
                .intValue();

        final int secondSavedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(secondSavedSubcategoryName)
                .categoryId(savedCategoryId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.subcategoryList", hasSize(foundALot)))
                .andExpect(jsonPath("$.subcategoryList[0].entityId", oneOf(firstSavedSubcategoryId, secondSavedSubcategoryId)))
                .andExpect(jsonPath("$.subcategoryList[0].name", oneOf(firstSavedSubcategoryName, secondSavedSubcategoryName)))
                .andExpect(jsonPath("$.subcategoryList[0].categoryId", is(savedCategoryId.intValue())))
                .andExpect(jsonPath("$.subcategoryList[1].entityId", oneOf(firstSavedSubcategoryId, secondSavedSubcategoryId)))
                .andExpect(jsonPath("$.subcategoryList[1].name", oneOf(firstSavedSubcategoryName, secondSavedSubcategoryName)))
                .andExpect(jsonPath("$.subcategoryList[1].categoryId", is(savedCategoryId.intValue())))
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
                .andExpect(jsonPath("$.subcategoryList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findById_found() throws Exception {
        final int foundOne = 1;
        final String savedSubcategoryName = randomString();

        final int savedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(savedSubcategoryName)
                .categoryId(savedCategoryId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + savedSubcategoryId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.subcategoryList", hasSize(foundOne)))
                .andExpect(jsonPath("$.subcategoryList[0].entityId", equalTo(savedSubcategoryId)))
                .andExpect(jsonPath("$.subcategoryList[0].name", is(savedSubcategoryName)))
                .andExpect(jsonPath("$.subcategoryList[0].categoryId", is(savedCategoryId.intValue())))
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
                .andExpect(jsonPath("$.subcategoryList", hasSize(noOneHasBeenFound)))
                .andExpect(jsonPath("$.total", is(noOneHasBeenFound)));
    }

    @Test
    public void findByName_found() throws Exception {
        final int foundOne = 1;
        final String savedSubcategoryName = randomString();

        final int savedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(savedSubcategoryName)
                .categoryId(savedCategoryId)
                .build())
                .getEntityId()
                .intValue();

        mockMvc.perform(get(ENDPOINT + "name/" + savedSubcategoryName))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.subcategoryList", hasSize(foundOne)))
                .andExpect(jsonPath("$.subcategoryList[0].entityId", equalTo(savedSubcategoryId)))
                .andExpect(jsonPath("$.subcategoryList[0].name", is(savedSubcategoryName)))
                .andExpect(jsonPath("$.subcategoryList[0].categoryId", is(savedCategoryId.intValue())))
                .andExpect(jsonPath("$.total", is(foundOne)));
    }

    @Test
    public void update_saveSubcategory() throws Exception {
        final boolean updated = true;

        SubcategoryUpdateRq rq = SubcategoryUpdateRq.builder()
                .name(randomString())
                .categoryId(savedCategoryId)
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
    public void update_saveSubcategory_subcategoryNameAlreadyExists() throws Exception {
        final boolean updated = false;
        final String subcategoryName = randomString();

        service.update(SubcategoryUpdateRq.builder()
                .name(subcategoryName)
                .categoryId(savedCategoryId)
                .build());

        SubcategoryUpdateRq rq = SubcategoryUpdateRq.builder()
                .name(subcategoryName)
                .categoryId(savedCategoryId)
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
    public void update_updateSubcategory() throws Exception {
        final boolean updated = true;

        final Long savedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(randomString())
                .categoryId(savedCategoryId)
                .build())
                .getEntityId();

        final String newSubcategoryName = randomString();
        SubcategoryUpdateRq rq = SubcategoryUpdateRq.builder()
                .entityId(savedSubcategoryId)
                .name(newSubcategoryName)
                .categoryId(savedCategoryId)
                .build();

        mockMvc.perform(post(ENDPOINT)
                .content(toJSON(rq))
                .contentType(JSON))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()))
                .andExpect(jsonPath("$.entityId", equalTo(savedSubcategoryId.intValue())))
                .andExpect(jsonPath("$.updated", is(updated)));

        SubcategoryGetRs rs = service.findById(new SubcategoryGetByIdRq(savedSubcategoryId));

        assertEquals(1, rs.getSubcategoryList().size());
        assertEquals(newSubcategoryName, rs.getSubcategoryList().get(0).getName());
    }

    @Test
    public void update_updateSubcategory_categoryDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        SubcategoryUpdateRq rq = SubcategoryUpdateRq.builder()
                .name(randomString())
                .categoryId(currentId)
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
    public void update_updateSubcategory_subcategoryDoesNotExistByCurrentId() throws Exception {
        final Long currentId = 999L;
        final boolean updated = false;

        SubcategoryUpdateRq rq = SubcategoryUpdateRq.builder()
                .entityId(currentId)
                .name(randomString())
                .categoryId(savedCategoryId)
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
        final Long savedSubcategoryId = service.update(SubcategoryUpdateRq.builder()
                .name(randomString())
                .categoryId(savedCategoryId)
                .build())
                .getEntityId();

        mockMvc.perform(delete(ENDPOINT + savedSubcategoryId))
                .andExpect(content().contentType(JSON))
                .andExpect(jsonPath("$.status.code", is(SUCCESS)))
                .andExpect(jsonPath("$.status.description", nullValue()));

        SubcategoryGetRs rs = service.findById(new SubcategoryGetByIdRq(savedSubcategoryId));

        assertEquals(0, rs.getSubcategoryList().size());
    }
}

package ru.gold.ordance.board.api.rest.heir.base.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.board.api.rest.heir.base.SubcategoryRestController;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.subcategory.*;
import ru.gold.ordance.board.web.service.base.SubcategoryWebService;

import static ru.gold.ordance.board.api.swagger.example.ApiExamples.ApiSubcategory.FIND_INVALID_RQ;
import static ru.gold.ordance.board.api.swagger.example.ApiExamples.ApiSubcategory.FIND_CALL_ERROR;
import static ru.gold.ordance.board.api.swagger.example.ApiExamples.ApiSubcategory.FIND_SUCCESS;
import static ru.gold.ordance.board.api.swagger.example.ApiExamples.Common.*;
import static ru.gold.ordance.board.api.swagger.example.ApiExamples.Common.UPDATE_CALL_ERROR;
import static ru.gold.ordance.board.api.utils.RequestUtils.*;
import static ru.gold.ordance.board.api.validation.Validation.validate;

@RestController
@RequestMapping(value = "/api/v1/subcategories/")
public class SubcategoryRestControllerImpl implements SubcategoryRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubcategoryRestControllerImpl.class);

    private final SubcategoryWebService service;

    public SubcategoryRestControllerImpl(SubcategoryWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    @Operation(summary = "Get all subcategories", tags = "search")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request. Size of the list is {0, ..., n}.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_SUCCESS))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_CALL_ERROR)))
    })
    public SubcategoryGetRs findAll() {
        try {
            LOGGER.info("Get all received.");

            SubcategoryGetRs rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            SubcategoryGetRs rs = SubcategoryGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Get subcategory by id", tags = "search")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request. Size of the list is {0 or 1}.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_SUCCESS))),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_INVALID_RQ))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_CALL_ERROR)))
    })
    public SubcategoryGetRs findById(@PathVariable Long entityId) {
        SubcategoryGetByIdRq rq = new SubcategoryGetByIdRq(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            SubcategoryGetRs rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            SubcategoryGetRs rs = SubcategoryGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/name/{name}", produces = JSON)
    @Operation(summary = "Get subcategory by name", tags = "search")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request. Size of the list is {0 or 1.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_SUCCESS))),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_INVALID_RQ))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(FIND_CALL_ERROR)))
    })
    public SubcategoryGetRs findByName(@PathVariable String name) {
        SubcategoryGetByNameRq rq = new SubcategoryGetByNameRq(name);

        try {
            LOGGER.info("Get by name request received: {}", rq);

            validate(rq);
            SubcategoryGetRs rs = service.findByName(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            SubcategoryGetRs rs = SubcategoryGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "(Save OR update) subcategory", tags = "save")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(UPDATE_SUCCESS))),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(UPDATE_INVALID_RQ))),

            @ApiResponse(responseCode = "VIOLATES_CONSTRAINT",
                    description = CONSTRAINT_MESSAGE,
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(UPDATE_VIOLATES_CONSTRAINT))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(UPDATE_CALL_ERROR)))
    })
    public SubcategoryUpdateRs update(@RequestBody SubcategoryUpdateRq rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            SubcategoryUpdateRs rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            SubcategoryUpdateRs rs = SubcategoryUpdateRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Delete subcategory by id", tags = "delete")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(DELETE_SUCCESS))),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(DELETE_INVALID_RQ))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(DELETE_CALL_ERROR)))
    })
    public SubcategoryDeleteByIdRs deleteById(@PathVariable Long entityId) {
        SubcategoryDeleteByIdRq rq = new SubcategoryDeleteByIdRq(entityId);

        try {
            LOGGER.info("Delete request received: {}", rq);

            validate(rq);
            SubcategoryDeleteByIdRs rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            SubcategoryDeleteByIdRs rs = SubcategoryDeleteByIdRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

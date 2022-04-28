package ru.gold.ordance.board.api.rest.heir.base.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.board.api.rest.heir.base.AdvertisementRestController;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.advertisement.*;
import ru.gold.ordance.board.web.service.base.AdvertisementWebService;

import static ru.gold.ordance.board.api.swagger.example.ApiExamples.ApiAdvertisement.*;
import static ru.gold.ordance.board.api.swagger.example.ApiExamples.Common.*;
import static ru.gold.ordance.board.api.utils.RequestUtils.*;
import static ru.gold.ordance.board.api.validation.Validation.validate;

@RestController
@RequestMapping(value = "/api/v1/advertisements/")
public class AdvertisementRestControllerImpl implements AdvertisementRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisementRestControllerImpl.class);

    private final AdvertisementWebService service;

    public AdvertisementRestControllerImpl(AdvertisementWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    @Operation(summary = "Get all advertisements", tags = "search")
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
    public AdvertisementGetRs findAll() {
        try {
            LOGGER.info("Get all received.");

            AdvertisementGetRs rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AdvertisementGetRs rs = AdvertisementGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Get advertisement by id", tags = "search")
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
    public AdvertisementGetRs findById(@PathVariable Long entityId) {
        AdvertisementGetByIdRq rq = new AdvertisementGetByIdRq(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            AdvertisementGetRs rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AdvertisementGetRs rs = AdvertisementGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/name/{name}", produces = JSON)
    @Operation(summary = "Get advertisement by name", tags = "search")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request. Size of the list is {0, ..., n}.",
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
    public AdvertisementGetRs findByName(@PathVariable String name) {
        AdvertisementGetByNameRq rq = new AdvertisementGetByNameRq(name);

        try {
            LOGGER.info("Get by name request received: {}", rq);

            validate(rq);
            AdvertisementGetRs rs = service.findByName(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AdvertisementGetRs rs = AdvertisementGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "(Save OR update) advertisement", tags = "save")
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
    public AdvertisementUpdateRs update(@RequestBody AdvertisementUpdateRq rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            AdvertisementUpdateRs rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AdvertisementUpdateRs rs = AdvertisementUpdateRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Delete advertisement by id", tags = "delete")
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
    public AdvertisementDeleteByIdRs deleteById(@PathVariable Long entityId) {
        AdvertisementDeleteByIdRq rq = new AdvertisementDeleteByIdRq(entityId);

        try {
            LOGGER.info("Delete request received: {}", rq);

            validate(rq);
            AdvertisementDeleteByIdRs rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AdvertisementDeleteByIdRs rs = AdvertisementDeleteByIdRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

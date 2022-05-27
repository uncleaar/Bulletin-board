package ru.gold.ordance.board.web.rest.heir.base.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.board.web.rest.heir.base.LnkLocalityStreetRestController;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.lnk.*;
import ru.gold.ordance.board.web.service.base.LnkLocalityStreetWebService;

import static ru.gold.ordance.board.web.swagger.example.ApiExamples.ApiLnkLocalityStreet.*;
import static ru.gold.ordance.board.web.swagger.example.ApiExamples.Common.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.web.validation.Validation.validate;

@RestController
@RequestMapping(value = "/api/v1/links-locality-street/")
@CrossOrigin(origins = "${cross-origin}")
public class LnkLocalityStreetRestControllerImpl implements LnkLocalityStreetRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LnkLocalityStreetRestControllerImpl.class);

    private final LnkLocalityStreetWebService service;

    public LnkLocalityStreetRestControllerImpl(LnkLocalityStreetWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    @Operation(summary = "Get all link locality-street", tags = "search")
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
    public LnkLocalityStreetGetRs findAll() {
        try {
            LOGGER.info("Get all received.");

            LnkLocalityStreetGetRs rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetGetRs rs = LnkLocalityStreetGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Get link locality-street by id", tags = "search")
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
    public LnkLocalityStreetGetRs findById(@PathVariable Long entityId) {
        LnkLocalityStreetGetByIdRq rq = new LnkLocalityStreetGetByIdRq(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            LnkLocalityStreetGetRs rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetGetRs rs = LnkLocalityStreetGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/locality/{localityId}", produces = JSON)
    @Operation(summary = "Get link locality-street by localityId", tags = "search")
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
    public LnkLocalityStreetGetRs findByLocality(@PathVariable Long localityId) {
        LnkLocalityStreetGetByLocalityRq rq = new LnkLocalityStreetGetByLocalityRq(localityId);

        try {
            LOGGER.info("Get by localityId request received: {}", rq);

            validate(rq);
            LnkLocalityStreetGetRs rs = service.findByLocality(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetGetRs rs = LnkLocalityStreetGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/street/{streetId}", produces = JSON)
    @Operation(summary = "Get link locality-street by streetId", tags = "search")
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
    public LnkLocalityStreetGetRs findByStreet(@PathVariable Long streetId) {
        LnkLocalityStreetGetByStreetRq rq = new LnkLocalityStreetGetByStreetRq(streetId);

        try {
            LOGGER.info("Get by streetId request received: {}", rq);

            validate(rq);
            LnkLocalityStreetGetRs rs = service.findByStreet(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetGetRs rs = LnkLocalityStreetGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/locality/{localityId}/street/{streetId}", produces = JSON)
    @Operation(summary = "Get link locality-street by localityId and streetId", tags = "search")
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
    public LnkLocalityStreetGetRs findByLocalityAndStreet(@PathVariable Long localityId, @PathVariable Long streetId) {
        LnkLocalityStreetGetByLSRq rq = LnkLocalityStreetGetByLSRq.builder()
                .localityId(localityId)
                .streetId(streetId)
                .build();

        try {
            LOGGER.info("Get by localityId and streetId request received: {}", rq);

            validate(rq);
            LnkLocalityStreetGetRs rs = service.findByLocalityAndStreet(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetGetRs rs = LnkLocalityStreetGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "(Save OR update) link locality-street", tags = "save")
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
    public LnkLocalityStreetUpdateRs update(@RequestBody LnkLocalityStreetUpdateRq rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            LnkLocalityStreetUpdateRs rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetUpdateRs rs = LnkLocalityStreetUpdateRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @DeleteMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Delete link locality-street by id", tags = "delete")
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
    public LnkLocalityStreetDeleteByIdRs deleteById(@PathVariable Long entityId) {
        LnkLocalityStreetDeleteByIdRq rq = new LnkLocalityStreetDeleteByIdRq(entityId);

        try {
            LOGGER.info("Delete request received: {}", rq);

            validate(rq);
            LnkLocalityStreetDeleteByIdRs rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            LnkLocalityStreetDeleteByIdRs rs = LnkLocalityStreetDeleteByIdRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

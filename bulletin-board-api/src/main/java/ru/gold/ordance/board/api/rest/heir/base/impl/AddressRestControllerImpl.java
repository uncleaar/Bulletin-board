package ru.gold.ordance.board.api.rest.heir.base.impl;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.board.api.rest.heir.base.AddressRestController;
import ru.gold.ordance.board.model.api.domain.Status;
import ru.gold.ordance.board.model.api.domain.address.*;
import ru.gold.ordance.board.web.service.base.AddressWebService;

import static ru.gold.ordance.board.api.swagger.example.ApiExamples.ApiAddress.*;
import static ru.gold.ordance.board.api.swagger.example.ApiExamples.Common.*;
import static ru.gold.ordance.board.api.utils.RequestUtils.*;
import static ru.gold.ordance.board.api.utils.RequestUtils.handleResponse;
import static ru.gold.ordance.board.api.validation.Validation.validate;

@RestController
@RequestMapping(value = "/api/v1/addresses/")
public class AddressRestControllerImpl implements AddressRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressRestControllerImpl.class);

    private final AddressWebService service;

    public AddressRestControllerImpl(AddressWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    @Operation(summary = "Get all addresses", tags = "search")
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
    public AddressGetRs findAll() {
        try {
            LOGGER.info("Get all received.");

            AddressGetRs rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AddressGetRs rs = AddressGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Get address by id", tags = "search")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request. Size of the list is {0 or 1}",
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
    public AddressGetRs findById(@PathVariable Long entityId) {
        AddressGetByIdRq rq = new AddressGetByIdRq(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            AddressGetRs rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AddressGetRs rs = AddressGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "(Save OR update) address", tags = "save")
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
    public AddressUpdateRs update(@RequestBody AddressUpdateRq rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            AddressUpdateRs rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AddressUpdateRs rs = AddressUpdateRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Delete address by id", tags = "delete")
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
    public AddressDeleteByIdRs deleteById(@PathVariable Long entityId) {
        AddressDeleteByIdRq rq = new AddressDeleteByIdRq(entityId);

        try {
            LOGGER.info("Delete request received: {}", rq);

            validate(rq);
            AddressDeleteByIdRs rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AddressDeleteByIdRs rs = AddressDeleteByIdRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

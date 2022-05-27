package ru.gold.ordance.board.web.rest.heir.base.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.board.web.rest.heir.base.ClientRestController;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.client.*;
import ru.gold.ordance.board.web.service.base.ClientWebService;

import static ru.gold.ordance.board.web.swagger.example.ApiExamples.ApiClient.*;
import static ru.gold.ordance.board.web.swagger.example.ApiExamples.Common.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.web.validation.Validation.validate;

@RestController
@RequestMapping("/api/v1/clients/")
@CrossOrigin(origins = "${cross-origin}")
public class ClientRestControllerImpl implements ClientRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestControllerImpl.class);

    private final ClientWebService service;

    public ClientRestControllerImpl(ClientWebService service) {
        this.service = service;
    }

    @Override
    @GetMapping(produces = JSON)
    @Operation(summary = "Get all clients", tags = "search")
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
    public ClientGetRs findAll() {
        try {
            LOGGER.info("Get all received.");

            ClientGetRs rs = service.findAll();
            handleResponse(LOGGER, rs, null, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetRs rs = ClientGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Get client by id", tags = "search")
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
    public ClientGetRs findById(@PathVariable Long entityId) {
        ClientGetByIdRq rq = new ClientGetByIdRq(entityId);

        try {
            LOGGER.info("Get by id request received: {}", rq);

            validate(rq);
            ClientGetRs rs = service.findById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetRs rs = ClientGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/name/{name}", produces = JSON)
    @Operation(summary = "Get client by name", tags = "search")
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
    public ClientGetRs findByName(@PathVariable String name) {
        ClientGetByNameRq rq = new ClientGetByNameRq(name);

        try {
            LOGGER.info("Get by name request received: {}", rq);

            validate(rq);
            ClientGetRs rs = service.findByName(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetRs rs = ClientGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @GetMapping(value = "/login/{login}", produces = JSON)
    @Operation(summary = "Get client by name", tags = "search")
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
    public ClientGetRs findByLogin(@PathVariable String login) {
        ClientGetByLoginRq rq = new ClientGetByLoginRq(login);

        try {
            LOGGER.info("Get by login request received: {}", rq);

            validate(rq);
            ClientGetRs rs = service.findByLogin(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientGetRs rs = ClientGetRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "Save client", tags = "save")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(SAVE_SUCCESS))),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(SAVE_INVALID_RQ))),

            @ApiResponse(responseCode = "VIOLATES_CONSTRAINT",
                    description = CONSTRAINT_MESSAGE,
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(SAVE_VIOLATES_CONSTRAINT))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(SAVE_CALL_ERROR)))
    })
    public ClientSaveRs save(@RequestBody ClientSaveRq rq) {
        try {
            LOGGER.info("Save request received: {}", rq);

            validate(rq);
            ClientSaveRs rs = service.save(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientSaveRs rs = ClientSaveRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @PutMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "Update client", tags = "save")
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
    public ClientUpdateRs update(@RequestBody ClientUpdateRq rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            ClientUpdateRs rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientUpdateRs rs = ClientUpdateRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }

    @Override
    @DeleteMapping(value = "/{entityId}", produces = JSON)
    @Operation(summary = "Delete client by id", tags = "delete")
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
    public ClientDeleteByIdRs deleteById(@PathVariable Long entityId) {
        ClientDeleteByIdRq rq = new ClientDeleteByIdRq(entityId);

        try {
            LOGGER.info("Delete request received: {}", rq);

            validate(rq);
            ClientDeleteByIdRs rs = service.deleteById(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ClientDeleteByIdRs rs = ClientDeleteByIdRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

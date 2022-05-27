package ru.gold.ordance.board.web.rest.heir.auth.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.gold.ordance.board.web.rest.heir.auth.AuthenticationRestController;
import ru.gold.ordance.board.web.rest.heir.base.impl.ClientRestControllerImpl;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.auth.*;
import ru.gold.ordance.board.web.service.auth.AuthWebService;

import static ru.gold.ordance.board.web.swagger.example.ApiExamples.ApiAuth.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.web.validation.Validation.validate;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "${cross-origin}")
public class AuthenticationRestControllerImpl implements AuthenticationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestControllerImpl.class);

    private final AuthWebService service;

    public AuthenticationRestControllerImpl(AuthWebService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/login")
    @Operation(summary = "Authorization for the user", tags = "authorization")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = {@ExampleObject(LOGIN_SUCCESS)})),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(LOGIN_INVALID_RQ))),

            @ApiResponse(responseCode = "VIOLATES_CONSTRAINT",
                    description = UNAUTHORIZED_MESSAGE,
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(LOGIN_UNAUTHORIZED))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(LOGIN_CALL_ERROR)))
    })
    public AuthLoginRs login(@RequestBody AuthLoginRq rq) {
        try {
            LOGGER.info("Login request received: {}", rq);

            validate(rq);
            AuthLoginRs rs = service.login(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AuthLoginRs rs = AuthLoginRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @PostMapping(value = "/registration", produces = JSON, consumes = JSON)
    @Operation(summary = "Registration for the user", tags = "registration")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = {@ExampleObject(REGISTRATION_SUCCESS)})),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(REGISTRATION_INVALID_RQ))),

            @ApiResponse(responseCode = "VIOLATES_CONSTRAINT",
                    description = CONSTRAINT_MESSAGE,
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(REGISTRATION_VIOLATES_CONSTRAINT))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(REGISTRATION_CALL_ERROR)))
    })
    public AuthRegistrationRs registration(@RequestBody AuthRegistrationRq rq) {
        try {
            LOGGER.info("Login request received: {}", rq);

            validate(rq);
            AuthRegistrationRs rs = service.registration(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AuthRegistrationRs rs = AuthRegistrationRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }

    @Override
    @GetMapping("/token/{token}")
    @Operation(summary = "Validation token", tags = "token")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = {@ExampleObject(TOKEN_SUCCESS)})),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(TOKEN_INVALID_RQ))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(TOKEN_CALL_ERROR)))
    })
    public AuthTokenLifeRs validationToken(@PathVariable String token) {
        AuthTokenLifeRq rq = new AuthTokenLifeRq(token);

        try {
            LOGGER.info("Token request received: {}", rq);

            validate(rq);
            AuthTokenLifeRs rs = service.validationToken(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            AuthTokenLifeRs rs = AuthTokenLifeRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, null, e);

            return rs;
        }
    }
}

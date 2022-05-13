package ru.gold.ordance.board.web.rest.heir.complex.impl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gold.ordance.board.web.rest.heir.complex.ComplexAddressRestController;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRs;
import ru.gold.ordance.board.web.service.complex.ComplexAddressWebService;

import static ru.gold.ordance.board.web.swagger.example.ApiExamples.ApiComplexAddress.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.*;
import static ru.gold.ordance.board.web.utils.RequestUtils.handleResponse;
import static ru.gold.ordance.board.web.validation.Validation.validate;

@RestController
@RequestMapping(value = "/api/v1/complex-address/")
public class ComplexAddressRestControllerImpl implements ComplexAddressRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplexAddressRestControllerImpl.class);

    private final ComplexAddressWebService service;

    public ComplexAddressRestControllerImpl(ComplexAddressWebService service) {
        this.service = service;
    }

    @Override
    @PostMapping(produces = JSON, consumes = JSON)
    @Operation(summary = "Complex save address", tags = "complex")
    @ApiResponses({
            @ApiResponse(responseCode = "SUCCESS",
                    description = "Success request.",
                    content = @Content(mediaType = JSON,
                            examples = {@ExampleObject(COMPLEX_ADDRESS_SUCCESS)})),

            @ApiResponse(responseCode = "INVALID_RQ",
                    description = "Invalid request.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(COMPLEX_ADDRESS_INVALID_RQ))),

            @ApiResponse(responseCode = "CALL_ERROR",
                    description = "Internal Server Error.",
                    content = @Content(mediaType = JSON,
                            examples = @ExampleObject(COMPLEX_ADDRESS_CALL_ERROR)))
    })
    public ComplexAddressUpdateRs update(@RequestBody ComplexAddressUpdateRq rq) {
        try {
            LOGGER.info("Update request received: {}", rq);

            validate(rq);
            ComplexAddressUpdateRs rs = service.update(rq);
            handleResponse(LOGGER, rs, rq, null);

            return rs;
        } catch (Exception e) {
            Status status = toStatus(e);
            ComplexAddressUpdateRs rs = ComplexAddressUpdateRs.error(status.getCode(), status.getDescription());
            handleResponse(LOGGER, rs, rq, e);

            return rs;
        }
    }
}

package ru.gold.ordance.board.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import ru.gold.ordance.board.web.exception.ValidationException;
import ru.gold.ordance.board.web.api.Rq;
import ru.gold.ordance.board.web.api.Rs;
import ru.gold.ordance.board.web.api.Status;
import ru.gold.ordance.board.web.api.StatusCode;

public final class RequestUtils {
    public static final String JSON = "application/json";

    public static final String CONSTRAINT_MESSAGE = "Request violates the database constraint.";

    public static final String UNAUTHORIZED_MESSAGE = "Request contains incorrect data.";

    private final static ObjectMapper mapper = new ObjectMapper();

    private RequestUtils() {
    }

    public static void handleResponse(Logger LOGGER, Rs rs, Rq rq, Exception e) {
        Status status = rs.getStatus();

        if (status.getCode() == StatusCode.SUCCESS) {
            LOGGER.info("Request completed. request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.INVALID_RQ) {
            LOGGER.info("Request validation failed. request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.VIOLATES_CONSTRAINT) {
            LOGGER.error(CONSTRAINT_MESSAGE + " request: {}, response: {}", rq, rs);
            return;
        }

        if (status.getCode() == StatusCode.UNAUTHORIZED) {
            LOGGER.error(UNAUTHORIZED_MESSAGE + " request: {}, response: {}", rq, rs);
        } else {
            LOGGER.error("Request failed. request: {}, response: {}", rq, rs, e);
        }
    }

    public static Status toStatus(Exception e) {
        String errorMessage;
        StatusCode statusCode;

        if (e instanceof ValidationException) {
            errorMessage = e.getMessage();
            statusCode = StatusCode.INVALID_RQ;
        } else if (e instanceof DataIntegrityViolationException) {
            errorMessage = CONSTRAINT_MESSAGE;
            statusCode = StatusCode.VIOLATES_CONSTRAINT;
        } else if (e instanceof InternalAuthenticationServiceException) {
            errorMessage = UNAUTHORIZED_MESSAGE;
            statusCode = StatusCode.UNAUTHORIZED;
        } else {
            errorMessage = e.getMessage();
            statusCode = StatusCode.CALL_ERROR;
        }

        return new Status()
                .withCode(statusCode)
                .withDescription(errorMessage);
    }

    public static String toJSON(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}

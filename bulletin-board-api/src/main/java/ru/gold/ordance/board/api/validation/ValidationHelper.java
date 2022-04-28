package ru.gold.ordance.board.api.validation;

import ru.gold.ordance.board.api.exception.ValidationException;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isBlank;

final class ValidationHelper {
    private ValidationHelper() {
    }

    public static void error(String message) {
        throw new ValidationException(message);
    }

    public static void errorEntityId(Long entityId) {
        if (nonNull(entityId) && entityId < 1) {
            error("The entityId is not positive.");
        }
    }

    public static void errorName(String name) {
        errorString(name, "name");
    }

    public static void errorString(String string, String fieldName) {
        if (isNull(string)) {
            error("The " + fieldName + " is null.");
        }

        if (isBlank(string)) {
            error("The " + fieldName + " is empty.");
        }
    }

    public static void errorObjectId(Long objectId, String fieldName) {
        if (isNull(objectId)) {
            error("The " + fieldName + " is null.");
        }

        if (objectId < 1) {
            error("The " + fieldName + " is not positive.");
        }
    }
}

package ru.gold.ordance.board.api.validation;

import ru.gold.ordance.board.model.api.domain.*;

public final class Validation {
    private static final RequestValidation<GetRq> GET = new GetValidation();
    private static final RequestValidation<SaveRq> SAVE = new SaveValidation();
    private static final RequestValidation<UpdateRq> UPDATE = new UpdateValidation();
    private static final RequestValidation<DeleteByIdRq> DELETE = new DeleteByIdValidation();
    private static final RequestValidation<AuthRq> AUTH = new AuthValidation();

    private Validation() {
    }
    
    public static void validate(Rq rq) {
        if (rq instanceof GetRq) {
            GET.validate((GetRq) rq);
        } else if (rq instanceof SaveRq) {
            SAVE.validate((SaveRq) rq);
        } else if (rq instanceof UpdateRq) {
            UPDATE.validate((UpdateRq) rq);
        } else if (rq instanceof DeleteByIdRq) {
            DELETE.validate((DeleteByIdRq) rq);
        } else if (rq instanceof AuthRq) {
            AUTH.validate((AuthRq) rq);
        } else  {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }
}

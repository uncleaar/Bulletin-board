package ru.gold.ordance.board.web.validation;

import ru.gold.ordance.board.web.api.SaveRq;
import ru.gold.ordance.board.web.api.client.ClientSaveRq;
import ru.gold.ordance.board.web.api.photo.PhotoSaveRq;

import static ru.gold.ordance.board.web.utils.PhotoStorageUtils.getExtension;
import static ru.gold.ordance.board.web.utils.PhotoStorageUtils.whiteList;
import static ru.gold.ordance.board.web.validation.ValidationHelper.*;

class SaveValidation implements RequestValidation<SaveRq> {
    @Override
    public void validate(SaveRq rq) {
        if (rq instanceof ClientSaveRq) {
            validateRequest((ClientSaveRq) rq);
        } else if (rq instanceof PhotoSaveRq) {
            validateRequest((PhotoSaveRq) rq);
        } else {
            throw new IllegalArgumentException("The transmitted rq is not supported by the current method.");
        }
    }

    private void validateRequest(ClientSaveRq rq) {
        errorString(rq.getLogin(), "login");
        errorString(rq.getPassword(), "password");
        errorName(rq.getName());
        errorString(rq.getPhoneNumber(), "phoneNumber");
    }

    private void validateRequest(PhotoSaveRq rq) {
        errorTrue(rq.getFile().isEmpty(), "The file is missing.");
        errorString(rq.getFile().getOriginalFilename(), "fileName");
        errorTrue(!whiteList.contains(getExtension(rq.getFile().getOriginalFilename())),
                "The file extension does not exist in the whitelist.");
    }
}

package net.javaguides.ems.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AnnouncementNotFoundException extends RuntimeException{
    public AnnouncementNotFoundException(String message) {
        super(message);
    }
}

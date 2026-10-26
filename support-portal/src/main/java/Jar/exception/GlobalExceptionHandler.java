package Jar.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@RestControllerAdvice//centralized exception

public class GlobalExceptionHandler {
    @ExceptionHandler(NotLogin.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNotLogin(NotLogin ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

}

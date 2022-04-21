package api.console.exceptions;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handle(NotFoundException e) {
        String message = e.getMessage();
        logger.error(message, e);
        return new ErrorDto(message);
    }

//    @ResponseStatus(FORBIDDEN)
//    @ExceptionHandler(ForbiddenException.class)
//    public ErrorDto handle( e) {
//        String message = e.getMessage();
//        logger.error(message, e);
//        return new ErrorDto(message);
//    }
}

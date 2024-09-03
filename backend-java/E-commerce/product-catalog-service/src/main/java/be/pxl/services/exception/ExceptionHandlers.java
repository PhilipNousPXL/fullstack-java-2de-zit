package be.pxl.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponseBody> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
        //TODO: message?
        return new ResponseEntity<>(new ErrorResponseBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<ErrorResponseBody> handleBadRequestExceptions(ResourceNotFoundException ex) {
        //TODO: message?
        return new ResponseEntity<>(new ErrorResponseBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    ResponseEntity<ErrorResponseBody> handleUnAuthorizedExceptions(UnAuthorizedException exception) {
        return new ResponseEntity<>(new ErrorResponseBody(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    private record ErrorResponseBody(String message) {
    }

}

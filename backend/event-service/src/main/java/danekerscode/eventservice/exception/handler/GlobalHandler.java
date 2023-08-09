package danekerscode.eventservice.exception.handler;

import danekerscode.eventservice.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.function.BiFunction;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalHandler {


    @ExceptionHandler(NotFoundException.class)
    ProblemDetail handle(NotFoundException e) {
        return this.withDetails.apply(e, BAD_REQUEST);
    }

    private final BiFunction<RuntimeException, HttpStatus, ProblemDetail> withDetails =
            (e, status) ->
                    ProblemDetail.forStatusAndDetail(status, e.getMessage());

}

package danekerscode.exception.handler;

import danekerscode.exception.EmailRegisteredYetException;
import danekerscode.exception.InvalidRequestToRegistrationException;
import danekerscode.exception.UserNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail detail(UserNotFoundException e){
        return getDetail(e.getMessage() , 404);
    }

    @ExceptionHandler(EmailRegisteredYetException.class)
    ProblemDetail detail(EmailRegisteredYetException e){
        return getDetail(e.getMessage(),400);
    }

    @ExceptionHandler(InvalidRequestToRegistrationException.class)
    ProblemDetail detail(InvalidRequestToRegistrationException e){
        return getDetail(e.getMessage(),400);
    }

    private static ProblemDetail getDetail(String e, int code) {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(code), e);
    }
}



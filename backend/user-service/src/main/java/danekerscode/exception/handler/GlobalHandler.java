package danekerscode.exception.handler;

import danekerscode.exception.EmailRegisteredYetException;
import danekerscode.exception.UserNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ProblemDetail detail(UserNotFoundException e){
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400) , e.getMessage());
    }

    @ExceptionHandler(EmailRegisteredYetException.class)
    ProblemDetail detail(EmailRegisteredYetException e){
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400) , e.getMessage());
    }
}



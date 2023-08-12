package danekerscode.ticketservice.exception.handler;

import danekerscode.ticketservice.exception.TicketComponentNotFoundException;
import danekerscode.ticketservice.exception.TicketNotFoundException;
import danekerscode.ticketservice.exception.TicketReturnDateExpiredException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(TicketReturnDateExpiredException.class)
    ProblemDetail handle(TicketReturnDateExpiredException e){
        return toDetails(400,e.getMessage());
    }

    @ExceptionHandler(TicketNotFoundException.class)
    ProblemDetail handle(TicketNotFoundException e){
        return toDetails(404,e.getMessage());
    }

    @ExceptionHandler(TicketComponentNotFoundException.class)
    ProblemDetail handle(TicketComponentNotFoundException e){
        return toDetails(500,e.getMessage());
    }


    private ProblemDetail toDetails(int code , String detail){
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(code) , detail);
    }
}


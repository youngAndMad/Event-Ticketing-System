package danekerscode.ticketservice.exception;

public class TicketReturnDateExpiredException  extends RuntimeException{
    public TicketReturnDateExpiredException() {
        super("time to return ticket expired");
    }
}

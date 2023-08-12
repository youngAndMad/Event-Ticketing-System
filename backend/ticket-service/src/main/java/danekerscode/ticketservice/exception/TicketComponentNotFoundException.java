package danekerscode.ticketservice.exception;

public class TicketComponentNotFoundException extends RuntimeException{
    public TicketComponentNotFoundException(String componentName) {
        super("ticket component error" + componentName);
    }
}

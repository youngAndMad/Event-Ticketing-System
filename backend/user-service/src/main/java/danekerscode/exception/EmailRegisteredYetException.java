package danekerscode.exception;


public class EmailRegisteredYetException extends RuntimeException {
    public EmailRegisteredYetException() {
        super("email registered yet");
    }
}

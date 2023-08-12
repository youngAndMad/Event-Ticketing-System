package danekerscode.ticketservice.exception;

import org.bouncycastle.crypto.RuntimeCryptoException;

public class ErrorFromOtherServiceException extends RuntimeCryptoException {
    public ErrorFromOtherServiceException(String msg) {
        super("error from other service: " + msg);
    }
}

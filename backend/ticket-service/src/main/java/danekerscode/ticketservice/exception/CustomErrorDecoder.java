package danekerscode.ticketservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 404 -> new TicketComponentNotFoundException(response.reason());
            case 500 -> new ErrorFromOtherServiceException(response.reason());
            default -> throw new IllegalStateException("Unexpected value: " + response.status());
        };
    }
}

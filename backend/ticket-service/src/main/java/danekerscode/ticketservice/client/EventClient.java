package danekerscode.ticketservice.client;

import danekerscode.ticketservice.utils.Event;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "event"
//        , configuration = FeignClient.class
)
public interface EventClient {

    @GetMapping("{id}")
    Event getById(@PathVariable Long id);

}

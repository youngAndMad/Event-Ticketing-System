package danekerscode.ticketservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user"
//        , configuration = FeignClient.class
)
public interface UserClient {

    @GetMapping("{id}")
    Object findById(@PathVariable Long id);

}

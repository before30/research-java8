package cc.before30.controller.blocking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by before30 on 04/01/2017.
 */

@RestController
@Slf4j
public class RouterBlockingController {
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${sp.non_blocking.url}")
    private String SP_NON_BLOCKING_URL;

    @GetMapping("/router-blocking")
    public String blockingRouter(
            @RequestParam(value = "minMs", required = false, defaultValue = "0") int minMs,
            @RequestParam(value = "maxMs", required = false, defaultValue = "0") int maxMs) {

        
        String url = SP_NON_BLOCKING_URL + "?minMs={minMs}&maxMs={maxMs}";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class, minMs, maxMs);
        HttpStatus status = result.getStatusCode();
        return result.getBody();

    }
}

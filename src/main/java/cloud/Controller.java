package cloud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class Controller {
    @GetMapping("/hello")
    @AccessLimit
    public ResponseEntity<String> hello() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("complete");
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}

package og.prj.securityjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class Home {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/student")
    public String student() {
     return webClientBuilder.build().get().uri("http://student-service/").retrieve().bodyToMono(String.class).block();

    }

    @GetMapping("/teacher")
    public String teacher() {
        return "teacher";
    }

    @GetMapping("/admin")
    public String admin() {
        return webClientBuilder.build().get().uri("http://admin-service/").retrieve().bodyToMono(String.class).block();

    }
}

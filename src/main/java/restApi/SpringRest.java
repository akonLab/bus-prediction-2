package restApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
@SpringBootApplication
public class SpringRest {
    public static void main(String[] args) {
        SpringApplication.run(SpringRest.class);
    }

    @Scheduled(fixedRate = 5000)
    @RequestMapping("/buses")
    public void greeting() {
        System.out.println("Hello!!!");
    }
}

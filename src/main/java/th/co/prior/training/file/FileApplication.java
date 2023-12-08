package th.co.prior.training.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

    @Bean("maoBean")
    public Map<String, String> maoBean(){
        Map<String, String> x = new HashMap<>();
        x.put("xxxx", "xxx");
        return x;
    }

}

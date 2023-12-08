package th.co.prior.training.file.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ToolUtilsComponent {

    private Map<String, String> x;

    public Map<String, String> getX() {
        return x;
    }

    private ObjectMapper objectMapper;

    @PostConstruct
    public void init(){
        this.objectMapper = new ObjectMapper();
        x = new HashMap<>();
        x.put("secret", "asdfsdfsdfsdf");
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}

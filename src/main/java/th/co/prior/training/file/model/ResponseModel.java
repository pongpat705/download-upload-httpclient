package th.co.prior.training.file.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseModel<T> {
    private int code;
    private String message;
    private T data;
}

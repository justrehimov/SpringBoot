package az.spring.springboot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePageModel<T> {
    private PageData<T> result;
    private boolean error;
    private String message;
    private int code;
}

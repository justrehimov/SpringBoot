package az.spring.springboot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageData <T>{
    private T data;
    private Pagination pagination;
}

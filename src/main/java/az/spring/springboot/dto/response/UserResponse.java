package az.spring.springboot.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class UserResponse {
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private Integer age;
}

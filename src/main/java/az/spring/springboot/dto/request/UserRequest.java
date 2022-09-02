package az.spring.springboot.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    @NotNull(message = "First name can't be null")
    @NotBlank(message = "First name can't be blank")
    @Size(min = 3, max = 12, message = "First name size must be between 3 and 12 characters")
    @JsonProperty("first_name")
    private String firstName;


    @NotNull(message = "Last name can't be null")
    @NotBlank(message = "Last name can't be blank")
    @Size(min = 3, max = 12, message = "Last name size must be between 3 and 12 characters")
    @JsonProperty("last_name")
    private String lastName;

    @NotNull(message = "Age can't be null")
    private Integer age;
}

package az.spring.springboot.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpringException extends RuntimeException {

    public SpringException(String message) {
        super(message);
    }
}

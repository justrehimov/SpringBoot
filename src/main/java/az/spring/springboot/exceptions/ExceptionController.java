package az.spring.springboot.exceptions;

import az.spring.springboot.dto.response.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {SpringException.class,RuntimeException.class,Exception.class})
    public ResponseModel<Object> handleSpringException(SpringException ex) {
        return ResponseModel.builder()
                .result(new Object())
                .error(true)
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel<Object> handleValidationError(BindingResult bindingResult) {
        final String[] error = {""};
        bindingResult.getFieldErrors()
                .stream()
                .forEach(fieldError -> {
                    error[0] += fieldError.getDefaultMessage() + " \n";
                });

        return ResponseModel.builder()
                .result(new Object())
                .message(error[0])
                .error(true)
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
    }

}

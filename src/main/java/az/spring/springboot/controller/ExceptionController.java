package az.spring.springboot.controller;

import az.spring.springboot.dto.response.ResponseModel;
import az.spring.springboot.dto.response.UserResponse;
import az.spring.springboot.exceptions.SpringException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(SpringException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseModel<UserResponse> handleSpringException(SpringException ex) {
        return ResponseModel.<UserResponse>builder()
                .result(null)
                .error(true)
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel<UserResponse> handleValidationError(BindingResult bindingResult) {
        final String[] error = {""};
        bindingResult.getFieldErrors()
                .stream()
                .forEach(fieldError -> {
                    error[0] += fieldError.getDefaultMessage() + " \n";
                });

        return ResponseModel.<UserResponse>builder()
                .result(null)
                .message(error[0])
                .error(true)
                .code(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .build();
    }

}

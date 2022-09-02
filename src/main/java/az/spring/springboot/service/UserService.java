package az.spring.springboot.service;

import az.spring.springboot.dto.request.UserRequest;
import az.spring.springboot.dto.response.ResponseModel;
import az.spring.springboot.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    ResponseModel<List<UserResponse>> list();

    ResponseModel<UserResponse> save(UserRequest user);

    ResponseModel<UserResponse> get(Long id);

    ResponseModel<UserResponse> update(UserRequest user, Long id);

    ResponseModel<UserResponse> delete(Long id);


}

package az.spring.springboot.service;

import az.spring.springboot.dto.request.UserRequest;
import az.spring.springboot.dto.response.ResponseModel;
import az.spring.springboot.dto.response.UserAddressResponse;
import az.spring.springboot.dto.response.UserResponse;
import az.spring.springboot.entity.User;

import java.util.List;

public interface UserService {
    ResponseModel<List<UserResponse>> list();

    ResponseModel<UserResponse> save(UserRequest user);

    ResponseModel<UserResponse> get(Long id);

    ResponseModel<UserResponse> update(UserRequest user, Long id);

    ResponseModel<UserResponse> delete(Long id);


    ResponseModel<List<String>> getNames();

    ResponseModel<List<User>> getByAgeGreatherThan(Integer age);

    ResponseModel<List<String>> getSurnames();

    List<String> getNamesByName(String name);

    List<String> getSurnamesBySurname(String surname);

    List<UserAddressResponse> getUserAddressResponses();
}

package az.spring.springboot.controller;

import az.spring.springboot.dto.request.UserRequest;
import az.spring.springboot.dto.response.ResponseModel;
import az.spring.springboot.dto.response.UserAddressResponse;
import az.spring.springboot.dto.response.UserResponse;
import az.spring.springboot.entity.User;
import az.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseModel<List<UserResponse>> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public ResponseModel<UserResponse> get(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    @PostMapping("/save")
    public ResponseModel<UserResponse> save(@RequestBody @Valid UserRequest userRequest) {
        return userService.save(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseModel<UserResponse> update(@RequestBody @Valid UserRequest userRequest, @PathVariable Long id) {
        return userService.update(userRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseModel<UserResponse> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @GetMapping("/name/list")
    public ResponseModel<List<String>> getNames(){
        return userService.getNames();
    }

    @GetMapping("/surname/list")
    public ResponseModel<List<String>> getSurnames(){
        return userService.getSurnames();
    }

    @GetMapping("/age/{age}")
    public ResponseModel<List<User>> getByAgeGreatherThan(@PathVariable("age") Integer age){
        return userService.getByAgeGreatherThan(age);
    }

    @GetMapping("/name/{name}")
    public List<String> getNamesByName(@PathVariable("name") String name){
        return userService.getNamesByName(name);
    }

    @GetMapping("/surname/{surname}")
    public List<String> getSurnamesBySurname(@PathVariable("surname") String surname){
        return userService.getSurnamesBySurname(surname);
    }

    @GetMapping("/useraddress/list")
    public List<UserAddressResponse> getUserAddressResponses(){
        return userService.getUserAddressResponses();
    }

}

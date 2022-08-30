package az.spring.springboot.controller;

import az.spring.springboot.entity.User;
import az.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable("id") Long id){
        return userService.get(id);
    }

    @PostMapping("/save")
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user,@PathVariable Long id){
        return userService.update(user,id);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable Long id){
        return userService.delete(id);
    }

}

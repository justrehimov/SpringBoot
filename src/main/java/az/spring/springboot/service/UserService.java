package az.spring.springboot.service;

import az.spring.springboot.entity.User;

import java.util.List;

public interface UserService {
    List<User> list();

    User save(User user);

    User get(Long id);
}

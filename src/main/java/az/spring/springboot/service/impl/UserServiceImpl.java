package az.spring.springboot.service.impl;

import az.spring.springboot.entity.User;
import az.spring.springboot.repository.UserRepository;
import az.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
       Optional<User> optionalUser =  userRepository.findById(id);
       return optionalUser.orElseThrow(()->new IllegalArgumentException("User not found"));
    }
}

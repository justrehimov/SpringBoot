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
    @Override
    public User update(User user, Long id) {
        Optional<User> savedUser=userRepository.findById(id);
        if (savedUser.isPresent()){
            savedUser.get().setFirstName(user.getFirstName());
            savedUser.get().setLastName(user.getLastName());
            savedUser.get().setAge(user.getAge());
            return userRepository.save(savedUser.get());
        }else {
            return savedUser.get();
        }
    }

    @Override
    public User delete(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return user.get();
        } else {
            return null;
        }
    }
}

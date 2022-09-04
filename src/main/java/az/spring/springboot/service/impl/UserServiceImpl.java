package az.spring.springboot.service.impl;

import az.spring.springboot.dto.request.UserRequest;
import az.spring.springboot.dto.response.ResponseModel;
import az.spring.springboot.dto.response.UserResponse;
import az.spring.springboot.entity.User;
import az.spring.springboot.exceptions.SpringException;
import az.spring.springboot.exceptions.StatusMessage;
import az.spring.springboot.repository.UserRepository;
import az.spring.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseModel<List<UserResponse>> list() {
        List<User> userList = userRepository.findAll();

        List<UserResponse> userResponseList = userList.stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

        return ResponseModel.<List<UserResponse>>builder()
                .result(userResponseList)
                .error(false)
                .message(StatusMessage.SUCCESS)
                .code(HttpStatus.OK.value())
                .build();

    }

    @Override
    public ResponseModel<UserResponse> save(UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return ResponseModel.<UserResponse>builder()
                .result(userResponse)
                .error(false)
                .message(StatusMessage.ERROR)
                .code(HttpStatus.OK.value())
                .build();

    }

    @Override
    public ResponseModel<UserResponse> get(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new SpringException(StatusMessage.USER_NOT_FOUND));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseModel.<UserResponse>builder()
                .result(userResponse)
                .error(false)
                .message(StatusMessage.SUCCESS)
                .code(HttpStatus.OK.value())
                .build();
    }


    @Override
    @Transactional
    public ResponseModel<UserResponse> update(UserRequest userRequest, Long id) {
        Optional<User> savedUser = userRepository.findById(id);
        if (savedUser.isPresent()) {

            User user = modelMapper.map(userRequest, User.class);
            user.setId(id);
            User updatedUser = userRepository.save(user);

            UserResponse userResponse = modelMapper.map(updatedUser, UserResponse.class);

            return ResponseModel.<UserResponse>builder()
                    .result(userResponse)
                    .error(false)
                    .code(HttpStatus.OK.value())
                    .message(StatusMessage.SUCCESS)
                    .build();

        } else {
            throw new SpringException(StatusMessage.USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseModel<UserResponse> delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            userRepository.delete(user);
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);

            return ResponseModel.<UserResponse>builder()
                    .result(userResponse)
                    .error(false)
                    .code(HttpStatus.OK.value())
                    .message(StatusMessage.SUCCESS)
                    .build();
        } else {
            throw new SpringException(StatusMessage.USER_NOT_FOUND);
        }
    }
}

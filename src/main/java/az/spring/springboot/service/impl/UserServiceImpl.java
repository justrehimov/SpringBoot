package az.spring.springboot.service.impl;

import az.spring.springboot.dao.UserDao;
import az.spring.springboot.dto.request.UserRequest;
import az.spring.springboot.dto.response.*;
import az.spring.springboot.entity.Address;
import az.spring.springboot.entity.User;
import az.spring.springboot.exceptions.SpringException;
import az.spring.springboot.exceptions.StatusMessage;
import az.spring.springboot.repository.AddressRepository;
import az.spring.springboot.repository.UserRepository;
import az.spring.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
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
    private final AddressRepository addressRepository;
    private final UserDao userDao;

    @Value("${default.page.element.size}")
    private int pageElementSize;

    @Override
    public ResponseModel<List<UserResponse>> list() {
        List<User> userList = userRepository.findAll();

        List<UserResponse> userResponseList = userList.stream()
                .map(user -> {
                    UserResponse userResponse = modelMapper.map(user, UserResponse.class);
                    userResponse.setAddress(user.getAddress().getAddress());
                    return userResponse;
                }).collect(Collectors.toList());

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
        Address address = new Address();
        address.setAddress(userRequest.getAddress());
        Address savedAddress = addressRepository.save(address);
        user.setAddress(savedAddress);
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setAddress(savedUser.getAddress().getAddress());
        return ResponseModel.<UserResponse>builder()
                .result(userResponse)
                .error(false)
                .message(StatusMessage.SUCCESS)
                .code(HttpStatus.OK.value())
                .build();

    }

    @Override
    public ResponseModel<UserResponse> get(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new SpringException(StatusMessage.USER_NOT_FOUND));
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setAddress(user.getAddress().getAddress());
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
            Address updatedAddress = new Address(user.getAddress().getId(), userRequest.getAddress());
            addressRepository.save(updatedAddress);
            user.setAddress(updatedAddress);
            User updatedUser = userRepository.save(user);
            UserResponse userResponse = modelMapper.map(updatedUser, UserResponse.class);
            userResponse.setAddress(user.getAddress().getAddress());
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
            userResponse.setAddress(user.getAddress().getAddress());
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
    public ResponseModel<List<String>> getNames() {
        List<String> nameList = userRepository.findUserNames();
        return ResponseModel.<List<String>>builder()
                .result(nameList)
                .code(200)
                .message(StatusMessage.SUCCESS)
                .build();
    }

    @Override
    public ResponseModel<List<User>> getByAgeGreatherThan(Integer age) {

        List<User> userList =  userRepository.findByAgeGreaterThan(age);

        return ResponseModel.<List<User>>builder()
                .result(userList)
                .error(false)
                .code(HttpStatus.OK.value())
                .message(StatusMessage.SUCCESS)
                .build();
    }

    @Override
    public ResponseModel<List<String>> getSurnames() {
        List<String> surnameList = userRepository.findSurnames();

        return ResponseModel.<List<String>>builder()
                .result(surnameList)
                .code(200)
                .message(StatusMessage.SUCCESS)
                .build();
    }

    @Override
    public List<String> getNamesByName(String name) {
        return userRepository.findUserNames(name);
    }

    @Override
    public List<String> getSurnamesBySurname(String surname) {
        return userRepository.findSurnames(surname);
    }

    @Override
    public List<UserAddressResponse> getUserAddressResponses() {
        return userRepository.getUserAddressResponse();
    }

    @Override
    public ResponsePageModel<List<UserResponse>> list(String filter, Integer age, String address, Integer page, Integer size) {

        int pageSize = size == null ? pageElementSize : size;
        int currentPage = page == null ? 0 : page;

        List<User> userList = userDao.list(filter, age, address, currentPage, pageSize);

        List<UserResponse> userResponseList = userList.stream().map(user -> {
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            userResponse.setAddress(user.getAddress().getAddress());
            return userResponse;
        }).collect(Collectors.toList());

        int totalElement = (int)userRepository.count();

        int totalPage = (int)Math.ceil(totalElement/(double)pageSize);


        Pagination pagination = Pagination.builder()
                .currentPage(currentPage)
                .offset(userResponseList.size())
                .totalElement(totalElement)
                .totalPage(totalPage)
                .build();

        PageData<List<UserResponse>> pageData = PageData.<List<UserResponse>>builder()
                .data(userResponseList)
                .pagination(pagination)
                .build();

        return ResponsePageModel.<List<UserResponse>>builder()
                .result(pageData)
                .message(StatusMessage.SUCCESS)
                .error(false)
                .code(HttpStatus.OK.value())
                .build();

    }
}

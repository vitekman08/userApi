package com.user.subscriptions.apiuser.service;

import com.user.subscriptions.apiuser.dto.UserDto;
import com.user.subscriptions.apiuser.exception.UserNotFoundException;
import com.user.subscriptions.apiuser.mappers.UserMapper;
import com.user.subscriptions.apiuser.model.User;
import com.user.subscriptions.apiuser.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("User not found"));

        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        User updateUser = userRepository.save(user);
        return userMapper.toUserDto(updateUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}

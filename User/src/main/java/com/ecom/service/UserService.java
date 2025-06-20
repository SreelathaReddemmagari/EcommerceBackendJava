package com.ecom.service;

import com.ecom.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface UserService {


        UserDto createUser(UserDto user);
        UserDto updateUser(UserDto userDto,Integer userId);
        UserDto getUserById(Integer userId);
        List<UserDto> getAllUsers();
        void deleteUser(Integer userId);

}

package com.ecom.serviceImpl;

import com.ecom.Repo.RoleRepo;
import com.ecom.Repo.UserRepo;
import com.ecom.entities.Role;
import com.ecom.entities.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.UserDto;
import com.ecom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepository;

    private User dtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAge(dto.getAge());
        user.setGender(dto.getGender());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setRole(dto.getRole());
        return user;
    }

    // Convert Entity to DTO
    private UserDto userToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setAge(user.getAge());
        dto.setGender(user.getGender());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override

    public UserDto createUser(UserDto userDto) {
        // Step 1: Encode the password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Step 2: Handle role assignment
        String roleName;
        if (userDto.getRole() == null || userDto.getRole().getRoleName() == null) {
            roleName = "USER"; // Default role name
        } else {
            roleName = userDto.getRole().getRoleName();
        }

        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            role = new Role();
            role.setRoleName(roleName);
            role = roleRepository.save(role);
        }

        // Step 3: Convert UserDto to User and assign the role
        User user = dtoToUser(userDto);
        user.setRole(role);

        // Step 4: Save the user to the repository
        User savedUser = userRepository.save(user);

        // Step 5: Convert back to UserDto and return
        return userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        User user = optionalUser.get();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAge(userDto.getAge());
        user.setGender(userDto.getGender());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());

        User updatedUser = userRepository.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        userRepository.delete(user);
    }
}

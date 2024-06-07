package com.springframework.springrecipeapp.services;

import com.springframework.springrecipeapp.commands.UserDto;
import com.springframework.springrecipeapp.domain.User;
import com.springframework.springrecipeapp.repsositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void saveUser(UserDto userDto);

    User findUserById(Long id);

    User findUserByEmail(String email);

}

package com.ptms.ptms.service;

import com.ptms.ptms.dto.AuthRequest;
import com.ptms.ptms.dto.JwtResponse;
import com.ptms.ptms.dto.UserDto;
import com.ptms.ptms.model.User;

import java.util.List;

public interface UserService {
    void registerUser(UserDto userDto);
    List<UserDto> getAllUsers();
    JwtResponse verify(AuthRequest authRequest);

    String deleteUser(Long id);
}

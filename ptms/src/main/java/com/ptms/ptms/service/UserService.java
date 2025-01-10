package com.ptms.ptms.service;

import com.ptms.ptms.dto.AuthRequest;
import com.ptms.ptms.dto.JwtResponse;
import com.ptms.ptms.model.User;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    List<User> getAllUsers();
    JwtResponse verify(AuthRequest authRequest);
}

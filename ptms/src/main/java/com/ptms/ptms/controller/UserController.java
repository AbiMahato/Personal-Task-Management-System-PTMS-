package com.ptms.ptms.controller;

import com.ptms.ptms.dto.AuthRequest;
import com.ptms.ptms.dto.JwtResponse;
import com.ptms.ptms.dto.RefreshTokenRequest;
import com.ptms.ptms.model.User;
import com.ptms.ptms.repository.RefreshTokenRepo;
import com.ptms.ptms.service.RefreshTokenService;
import com.ptms.ptms.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    public UserController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }
    @GetMapping
    public String index(HttpServletRequest request) {
        return "Welcome Chief";
    }
    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        return "Hello World"+ " " + request.getSession().getId();
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest authRequest){
        return userService.verify(authRequest);
    }

    @PostMapping("/refresh-token")
    public JwtResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }
}

package com.ptms.ptms.service;

import com.ptms.ptms.dto.AuthRequest;
import com.ptms.ptms.dto.JwtResponse;
import com.ptms.ptms.model.RefreshToken;
import com.ptms.ptms.model.User;
import com.ptms.ptms.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authManager;
    private final UserRepo userRepo;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenService refreshTokenService;
    @Autowired
    public UserServiceImpl(UserRepo userRepo, AuthenticationManager authManager ,JWTService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.authManager = authManager;
        this.refreshTokenService = refreshTokenService;
    }
    @Override
    public void registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
    @Override
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }
    @Override
    public JwtResponse verify(AuthRequest authRequest){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(user.getUserName());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .token(refreshToken.getToken()).build();
        } else {
//            return "fail";
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}

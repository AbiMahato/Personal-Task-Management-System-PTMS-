package com.ptms.ptms.service;

import com.ptms.ptms.dto.AuthRequest;
import com.ptms.ptms.dto.JwtResponse;
import com.ptms.ptms.dto.UserDto;
import com.ptms.ptms.mapper.TaskMapper;
import com.ptms.ptms.mapper.UserMapper;
import com.ptms.ptms.model.RefreshToken;
import com.ptms.ptms.model.Task;
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
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authManager;
    private final UserRepo userRepo;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenService refreshTokenService;
//    @Autowired
    public UserServiceImpl(UserRepo userRepo, AuthenticationManager authManager ,JWTService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.authManager = authManager;
        this.refreshTokenService = refreshTokenService;
    }
    @Override
    public void registerUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.converDtoToUserEntity(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);
    }
    @Override
    public List<UserDto> getAllUsers(){
        return userRepo.findAll()
                .stream()
                .map(UserMapper.INSTANCE::convertUserEntityToDto)
                .collect(Collectors.toList());
    }
    @Override
    public JwtResponse verify(AuthRequest authRequest){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(user.getUserName());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUserName());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUserName()))
                    .token(refreshToken.getToken()).build();
        } else {
//            return "fail";
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @Override
    public String deleteUser(Long id) {
        if (userRepo.findById(id).isEmpty())
            throw new RuntimeException("user with id " + id + " does not exist");
        User user = userRepo.findById(id).get();
        userRepo.delete(user);
        return "user deleted, id=" + id;
    }
}

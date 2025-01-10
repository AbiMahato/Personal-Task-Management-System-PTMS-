package com.ptms.ptms.service;

import com.ptms.ptms.dto.JwtResponse;
import com.ptms.ptms.dto.RefreshTokenRequest;
import com.ptms.ptms.model.RefreshToken;
import com.ptms.ptms.repository.RefreshTokenRepo;
import com.ptms.ptms.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private  final RefreshTokenRepo refreshTokenRepo;
    private final UserRepo userRepo;
    private final JWTService jwtService;
    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, UserRepo userRepo, JWTService jwtService) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepo.findByUserName(username))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return refreshTokenRepo.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return findByToken(refreshTokenRequest.getToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user.getUserName());
                    return JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in database!"));
    }
}

package com.zosh.user.service.service.impl;

import com.zosh.user.service.model.User;
import com.zosh.user.service.payload.dto.SignUpDTO;
import com.zosh.user.service.payload.response.AuthResponse;
import com.zosh.user.service.payload.response.TokenResponse;
import com.zosh.user.service.repository.UserRepository;
import com.zosh.user.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final KeyClockService keyClockService;
    @Override
    public AuthResponse login(String username, String password) throws Exception {
        TokenResponse tokenResponse = keyClockService.getAdminAccessToken(username, password,"password",null);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("login success");
        return authResponse;
    }

    @Override
    public AuthResponse signup(SignUpDTO req) throws Exception {
        keyClockService.createUser(req);

        User user = new User();
        user.setUserName(req.getUsername());
        user.setPassword(req.getPassword());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setFullName(req.getFirstName());
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        TokenResponse tokenResponse = keyClockService.getAdminAccessToken(req.getUsername(), req.getPassword(),"password",null);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setRole(user.getRole());
        authResponse.setMessage("Register success");
        return authResponse;
    }

    @Override
    public AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception {
        TokenResponse tokenResponse = keyClockService.getAdminAccessToken(null, null,"refresh_token",refreshToken);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setRefresh_token(tokenResponse.getRefreshToken());
        authResponse.setJwt(tokenResponse.getAccessToken());
        authResponse.setMessage("login success");
        return authResponse;
    }
}

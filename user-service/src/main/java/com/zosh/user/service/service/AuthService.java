package com.zosh.user.service.service;

import com.zosh.user.service.payload.dto.SignUpDTO;
import com.zosh.user.service.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username,String password) throws Exception;
    AuthResponse signup(SignUpDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}

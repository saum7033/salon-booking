package com.zosh.user.service.controller;

import com.zosh.user.service.payload.dto.LoginDTO;
import com.zosh.user.service.payload.dto.SignUpDTO;
import com.zosh.user.service.payload.response.AuthResponse;
import com.zosh.user.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignUpDTO req)throws Exception{
        AuthResponse res = authService.signup(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO req)throws Exception{
        AuthResponse res = authService.login(req.getUsername(),req.getPassword());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> getAccessToken(@PathVariable String refreshToken)throws Exception{
        AuthResponse res = authService.getAccessTokenFromRefreshToken(refreshToken);
        return ResponseEntity.ok(res);
    }

}

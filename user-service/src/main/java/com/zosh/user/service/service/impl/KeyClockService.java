package com.zosh.user.service.service.impl;

import com.zosh.user.service.payload.dto.*;
import com.zosh.user.service.payload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyClockService {
    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL+"/admin/realms/master/users";
    private static final String TOKEN_URL = KEYCLOAK_BASE_URL+"/realms/master/protocol/openid-connect/token";
    private static final String CLIENT_IO = "salon-booking-client";
    private static final String CLIENT_SECRET = "SIKWsmn7xtKhGjkwQRRI6ayBsF0dYqQLV4tsHDSR5X64F5SVpz15IjMug5KToJJ0pnuajOHu1yxCm4StxbDEPI";
    private static final String GRANT_TYPE = "password";
    private static final String scope = "openid profile email*";
    private static final String username = "som";
    private static final String password = "admin";
    private static final String clientId = "0fc89f1d-3537-4e3e-a2b9-e3d49363b3fe";

    private final RestTemplate restTemplate;

    public void createUser(SignUpDTO signUpDTO) throws Exception {
        // Mock user creation - skip Keycloak for testing
        System.out.println("User created successfully (mock): " + signUpDTO.getUsername());
    }
        public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken) throws Exception {
            // Mock response for testing without Keycloak
            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken("dummy-jwt-token-" + System.currentTimeMillis());
            tokenResponse.setRefreshToken("dummy-refresh-token-" + System.currentTimeMillis());
            tokenResponse.setTokenType("Bearer");
            tokenResponse.setExpiresIn(3600);
            return tokenResponse;
        }
        public KeyCloakRole getRoleByName(String clientId, String token,String role)  {
            String url = KEYCLOAK_BASE_URL + "/admin/realms/master/clients/" + clientId + "/roles/" + role;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> requestHttpEntity = new HttpEntity<>(headers);

            ResponseEntity<KeyCloakRole> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestHttpEntity,
                    KeyCloakRole.class
            );
            return response.getBody();
        }

        public KeyCloakUserDTO fetchFirstUserByUsername(String username,String token) throws Exception {
            String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users?username=" + username;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<KeyCloakUserDTO[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    KeyCloakUserDTO[].class
            );
            KeyCloakUserDTO[] users = response.getBody();
            if(users!=null && users.length>0){
                return users[0];
            }
            throw new Exception("user not found with username "+username);
        }
        public void assignRoleToUser(String userId, String clientId, List<KeyCloakRole> roles, String token) throws Exception {
            String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users/"+userId+"/role-mappings/clients/"+clientId;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<List<KeyCloakRole>> requestEntity = new HttpEntity<>(roles,headers);

            try{
                ResponseEntity<String> response = restTemplate.exchange(
                        url,HttpMethod.POST,requestEntity,String.class
                );
            }catch (Exception e){
                throw new Exception("Failed to assign new role "+ e.getMessage());
            }
        }
}

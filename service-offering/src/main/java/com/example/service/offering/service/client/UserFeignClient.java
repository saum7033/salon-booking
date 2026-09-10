package com.example.service.offering.service.client;

import com.example.service.offering.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping("/api/user/{id}")
    ResponseEntity<UserDTO> getUserById(
            @PathVariable("id") Long id
    ) throws Exception;

    @GetMapping("/api/user/profile")
    ResponseEntity<UserDTO> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws Exception;
}
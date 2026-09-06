package com.zosh.user.service.payload.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
}

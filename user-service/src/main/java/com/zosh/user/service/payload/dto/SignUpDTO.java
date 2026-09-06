package com.zosh.user.service.payload.dto;

import com.zosh.user.service.domain.UserRole;
import lombok.Data;

@Data
public class SignUpDTO {
    private String fullName;
    private String email;
    private String password;
    private String username;
    private UserRole role;
}

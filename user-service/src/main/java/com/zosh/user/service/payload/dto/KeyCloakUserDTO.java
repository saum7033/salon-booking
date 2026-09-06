package com.zosh.user.service.payload.dto;

import lombok.Data;

@Data
public class KeyCloakUserDTO {
    public String id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
}

package com.zosh.user.service.payload.dto;


import lombok.Data;

import java.util.Map;

@Data
public class KeyCloakRole
{
    public String id;
    private String name;
    private String description;
    private boolean composite;
    private boolean clientRole;
    private String containerId;
    private Map<String,Object> attributes;
}

package com.forceclose.Employee.model.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String roles;
    private String permissions;
}

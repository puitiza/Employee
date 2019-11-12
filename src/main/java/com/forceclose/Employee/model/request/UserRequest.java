package com.forceclose.Employee.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String username;
    private String password;
    private List<String> roles;
}

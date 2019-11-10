package com.forceclose.Employee.services.config;

import com.forceclose.Employee.model.entity.UserAccess;
import com.forceclose.Employee.model.request.UserRequest;

public interface JwtUserDetailsService {
    UserAccess register(UserRequest user);
}

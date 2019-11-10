package com.forceclose.Employee.config.jwt;

public class JwtProperties {
    public static final String SECRET = "employeeAPI";
    public static final long JWT_TOKEN_VALIDITY = 5*60*60;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}

package com.forceclose.Employee.config.jwt;

import com.forceclose.Employee.model.entity.access.UserAccess;
import com.forceclose.Employee.model.entity.access.relation.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class JwtUserInfo implements UserDetails {

    private UserAccess user;

    public JwtUserInfo(UserAccess user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Set<UserRole> userRoles = this.user.getUserRole();
        userRoles.forEach(p -> {
            p.getRole().getRolePrivilege().forEach(r -> {
                // Extract list of permissions (name) --- privilege
                GrantedAuthority authority = new SimpleGrantedAuthority(r.getPrivilege().getName());
                authorities.add(authority);
            });
            // Extract list of roles (ROLE_name)
            GrantedAuthority authority = new SimpleGrantedAuthority(p.getRole().getName());
            authorities.add(authority);
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isActivated();
    }
}

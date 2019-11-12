package com.forceclose.Employee.services.config;

import com.forceclose.Employee.config.jwt.JwtUserInfo;
import com.forceclose.Employee.model.entity.access.UserAccess;
import com.forceclose.Employee.model.request.UserRequest;
import com.forceclose.Employee.repository.access.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService, JwtUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserAccess user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new JwtUserInfo(user);
        }catch (Exception e){
            return  null;
        }
    }

    @Override
    public UserAccess register(UserRequest user) {
        /*UserAccess newUser = new UserAccess(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                user.getRoles(), user.getPermissions());
        return userRepository.save(newUser);*/
        return  null;
    }
}
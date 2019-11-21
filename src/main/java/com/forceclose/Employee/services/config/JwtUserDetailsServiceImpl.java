package com.forceclose.Employee.services.config;

import com.forceclose.Employee.config.jwt.JwtTokenUtil;
import com.forceclose.Employee.config.jwt.JwtUserInfo;
import com.forceclose.Employee.model.entity.access.UserAccess;
import com.forceclose.Employee.model.entity.access.relation.UserRole;
import com.forceclose.Employee.model.request.UserRequest;
import com.forceclose.Employee.repository.access.RoleRepository;
import com.forceclose.Employee.repository.access.UserRepository;
import com.forceclose.Employee.repository.access.relation.User_RoleRepository;
import com.forceclose.Employee.services.mail.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService, JwtUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private User_RoleRepository user_RoleRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmailServiceImpl emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserAccess user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new JwtUserInfo(user);
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public UserAccess register(UserRequest user) {
        try {
            UserAccess newUser = new UserAccess();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setActivated(true);
            newUser.setDateCreated(new Date());
            newUser.setUserCreated(jwtTokenUtil.getUsernameLogged());
            UserAccess finalUser = userRepository.save(newUser);

            final Set<UserRole> userRoles = new HashSet<>();
            user.getRoles().forEach(p -> {

                UserRole newUserRole = new UserRole();
                newUserRole.setUser(finalUser);
                newUserRole.setRole(roleRepository.findByName(p));
                newUserRole.setActivated(true);
                newUserRole.setDateCreated(new Date());

                UserRole userRole = user_RoleRepository.save(newUserRole);
                userRoles.add(userRole);
            });
            finalUser.setUserRole(userRoles);

            UserAccess newUserAccess = userRepository.save(finalUser);
            if(newUserAccess!=null){
                emailService.sendSimpleMessage("anthony.puitiza.02@gmail.com",
                        "CREACIÓN DE NUEVO USUARIO: " + newUserAccess.getUsername(),
                        "Se ha creado con éxito este nuevo usuario perro");
            }

            return newUserAccess;
        }catch (Exception e){
            String ya=e.getMessage();
            return null;
        }


    }
}
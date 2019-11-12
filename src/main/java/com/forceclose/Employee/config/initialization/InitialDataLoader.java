package com.forceclose.Employee.config.initialization;

import com.forceclose.Employee.model.entity.access.*;
import com.forceclose.Employee.model.entity.access.relation.RolePrivilege;
import com.forceclose.Employee.model.entity.access.relation.UserRole;
import com.forceclose.Employee.repository.access.*;
import com.forceclose.Employee.repository.access.relation.Role_PrivilegeRepository;
import com.forceclose.Employee.repository.access.relation.User_RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private User_RoleRepository user_RoleRepository;

    @Autowired
    private Role_PrivilegeRepository role_privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }
        // == create initial privileges
        final PrivilegeAccess readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final PrivilegeAccess writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final PrivilegeAccess passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");


        // == create initial roles
        final List<PrivilegeAccess> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, writePrivilege));
        final List<PrivilegeAccess> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));
        final RoleAccess adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final RoleAccess userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        createUserIfNotFound("admin", "admin123", new ArrayList<>(Arrays.asList(adminRole)));
        createUserIfNotFound("user", "user123", new ArrayList<>(Arrays.asList(userRole)));

        alreadySetup = true;
    }

    @Transactional
    private PrivilegeAccess createPrivilegeIfNotFound(final String name) {

        PrivilegeAccess privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new PrivilegeAccess(name);
            privilege.setActivated(true);
            privilege.setDateCreated(new Date());
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private RoleAccess createRoleIfNotFound(final String name, final Collection<PrivilegeAccess> privileges) {

        RoleAccess role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleAccess(name);
            role.setActivated(true);
            role.setDateCreated(new Date());

            RoleAccess finalRole = roleRepository.save(role);
            final Set<RolePrivilege> rolePrivileges = new HashSet<>();

            privileges.forEach(p -> {
                RolePrivilege newRolePrivilege = new RolePrivilege();
                newRolePrivilege.setRole(finalRole);
                newRolePrivilege.setPrivilege(p);
                newRolePrivilege.setActivated(true);
                newRolePrivilege.setDateCreated(new Date());

                RolePrivilege userRole = role_privilegeRepository.save(newRolePrivilege);
                rolePrivileges.add(userRole);
            });
            //update relation role to user
            finalRole.setRolePrivilege(rolePrivileges);
            role = roleRepository.save(finalRole);
        }
        return role;
    }

    @Transactional
    private UserAccess createUserIfNotFound(final String username, final String password, final Collection<RoleAccess> roles) {
        //save user
        UserAccess user = userRepository.findByUsername(username);
        if (user == null) {
            user = new UserAccess();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setActivated(true);
            user.setDateCreated(new Date());

            UserAccess finalUser = userRepository.save(user);
            //save role to user
            final Set<UserRole> userRoles = new HashSet<>();

            roles.forEach(p -> {
                UserRole newUserRole = new UserRole();
                newUserRole.setUser(finalUser);
                newUserRole.setRole(p);
                newUserRole.setActivated(true);
                newUserRole.setDateCreated(new Date());

                UserRole userRole = user_RoleRepository.save(newUserRole);
                userRoles.add(userRole);
            });
            //update relation role to user
            finalUser.setUserRole(userRoles);
            user = userRepository.save(finalUser);
        }
        return user;
    }
}
package com.forceclose.Employee.config.initialization;

import com.forceclose.Employee.model.entity.access.PrivilegeAccess;
import com.forceclose.Employee.model.entity.access.RoleAccess;
import com.forceclose.Employee.model.entity.access.UserAccess;
import com.forceclose.Employee.model.entity.access.relation.RolePrivilege;
import com.forceclose.Employee.model.entity.access.relation.UserRole;
import com.forceclose.Employee.repository.access.PrivilegeRepository;
import com.forceclose.Employee.repository.access.RoleRepository;
import com.forceclose.Employee.repository.access.UserRepository;
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
        final PrivilegeAccess viewUsersPrivilege = createPrivilegeIfNotFound("VIEW_USERS_PRIVILEGE");
        final PrivilegeAccess assignUsersByToRolePrivilege = createPrivilegeIfNotFound("ASSIGN_USERS_BY_TO_ROLE_PRIVILEGE");
        final PrivilegeAccess passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        final PrivilegeAccess manager_privilege = createPrivilegeIfNotFound("MANAGER_PRIVILEGE");
        final PrivilegeAccess director_privilege = createPrivilegeIfNotFound("DIRECTOR_PRIVILEGE");


        // == create initial roles
        final List<PrivilegeAccess> adminPrivileges = new ArrayList<>(Arrays.asList(
                readPrivilege, writePrivilege,
                viewUsersPrivilege, assignUsersByToRolePrivilege,
                manager_privilege, passwordPrivilege, director_privilege));

        final List<PrivilegeAccess> managerPrivileges = new ArrayList<>(Arrays.asList(
                viewUsersPrivilege, assignUsersByToRolePrivilege, manager_privilege));

        final List<PrivilegeAccess> supportPrivileges = new ArrayList<>(Arrays.asList(
                readPrivilege, writePrivilege, passwordPrivilege));

        final List<PrivilegeAccess> directorPrivileges = new ArrayList<>(Collections.singletonList(
                director_privilege));

        final RoleAccess adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final RoleAccess directorRole = createRoleIfNotFound("ROLE_DIRECTOR", directorPrivileges);
        final RoleAccess managerRole = createRoleIfNotFound("ROLE_MANAGER", managerPrivileges);
        final RoleAccess supportRole = createRoleIfNotFound("ROLE_SUPPORT", supportPrivileges);

        // == create initial user
        createUserIfNotFound("admin", "admin123", new ArrayList<>(Collections.singletonList(adminRole)));
        createUserIfNotFound("director", "director123", new ArrayList<>(Collections.singletonList(directorRole)));
        createUserIfNotFound("manager", "manager123", new ArrayList<>(Collections.singletonList(managerRole)));
        createUserIfNotFound("user", "user123", new ArrayList<>(Collections.singletonList(managerRole)));

        alreadySetup = true;
    }

    @Transactional
    PrivilegeAccess createPrivilegeIfNotFound(final String name) {

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
    RoleAccess createRoleIfNotFound(final String name, final Collection<PrivilegeAccess> privileges) {

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
    UserAccess createUserIfNotFound(final String username, final String password, final Collection<RoleAccess> roles) {
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
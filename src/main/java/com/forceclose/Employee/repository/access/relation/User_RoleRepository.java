package com.forceclose.Employee.repository.access.relation;

import com.forceclose.Employee.model.entity.access.relation.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User_RoleRepository extends JpaRepository<UserRole, Long> { }
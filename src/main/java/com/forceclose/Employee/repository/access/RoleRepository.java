package com.forceclose.Employee.repository.access;

import com.forceclose.Employee.model.entity.access.RoleAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleAccess, Long> {
    RoleAccess findByName(String name);
}
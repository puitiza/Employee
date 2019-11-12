package com.forceclose.Employee.repository.access.relation;

import com.forceclose.Employee.model.entity.access.relation.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Role_PrivilegeRepository extends JpaRepository<RolePrivilege, Long> { }
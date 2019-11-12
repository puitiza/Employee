package com.forceclose.Employee.repository.access;

import com.forceclose.Employee.model.entity.access.HistoricalAccess;
import com.forceclose.Employee.model.entity.access.PrivilegeAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalRepository extends JpaRepository<HistoricalAccess, Long> { }
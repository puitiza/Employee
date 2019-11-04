package com.forceclose.Employee.repository;

import com.forceclose.Employee.model.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccess, Long> {
	UserAccess findByUsername(String username);
}
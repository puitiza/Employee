package com.forceclose.Employee.repository.business;

import com.forceclose.Employee.model.entity.business.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
}

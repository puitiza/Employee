package com.forceclose.Employee.services;

import com.forceclose.Employee.model.entity.Persona;

import java.util.List;

public interface PersonaService {

    List<Persona> findAll();
    Persona register(Persona employee);
    Persona findById(long id);
    Persona update(long id, Persona employee);
    boolean deleteById(long id);
    //Persona login(LoginResponse employee);

}

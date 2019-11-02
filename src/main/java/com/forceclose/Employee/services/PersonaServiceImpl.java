package com.forceclose.Employee.services;

import com.forceclose.Employee.model.Persona;
import com.forceclose.Employee.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    public Persona register(Persona employee) {
        return null;
    }

    @Override
    public Persona findById(long id) {
        return null;
    }

    @Override
    public Persona update(long id, Persona employee) {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}

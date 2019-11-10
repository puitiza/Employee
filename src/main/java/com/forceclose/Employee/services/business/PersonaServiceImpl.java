package com.forceclose.Employee.services.business;

import com.forceclose.Employee.controller.PersonaController;
import com.forceclose.Employee.model.entity.Persona;
import com.forceclose.Employee.repository.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;
    private Logger logger = LoggerFactory.getLogger(PersonaController.class);

    @Override
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    public Persona register(Persona employee) {
        return personaRepository.save(employee);
    }

    @Override
    public Persona findById(long id) {
        return personaRepository.findById(id).orElseThrow();
    }

    @Override
    public Persona update(long id, Persona employee) {
        try {
            Persona employeeFound = findById(id);
            employeeFound.setId(id);
            employeeFound.setName(employee.getName());
            employeeFound.setLast_name(employee.getLast_name());
            return personaRepository.save(employeeFound);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deleteById(long id) {
        try {
            Persona employeeFound = findById(id);
            personaRepository.delete(employeeFound);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
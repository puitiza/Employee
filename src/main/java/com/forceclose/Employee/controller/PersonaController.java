package com.forceclose.Employee.controller;

import com.forceclose.Employee.model.entity.Persona;
import com.forceclose.Employee.services.business.PersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonaController{

    private Logger logger = LoggerFactory.getLogger(PersonaController.class);

    @Autowired
    private PersonaService personaService;

    @GetMapping("/employees")
    public HttpEntity<List<Persona>> listar(){
        List<Persona> employees = personaService.findAll();
        if (employees == null || employees.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public HttpEntity<Persona> register(@RequestBody Persona employee) {
        try {
            Persona employee_Registered = personaService.register(employee);
            return new ResponseEntity<>(employee_Registered, HttpStatus.CREATED);
            //return  employee_Registered;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            //return null;
        }
    }

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @GetMapping("/employees/{id}")
    public HttpEntity<Persona> findById(@PathVariable long id) {
        try {
            Persona employees = personaService.findById(id);
            return new ResponseEntity<>(employees, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/employees/{id}")
    public HttpEntity<Persona> update(@PathVariable final String id, @RequestBody Persona employee) {
        Persona employee_Updated = personaService.update(Long.parseLong(id), employee);
        return (employee_Updated != null) ? new ResponseEntity<>(employee_Updated, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/employees/{id}")
    public HttpEntity<Persona> delete(@PathVariable long id) {
        return (personaService.deleteById(id)) ? new ResponseEntity<>(null, HttpStatus.OK) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

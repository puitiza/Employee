package com.forceclose.Employee.controller;

import com.forceclose.Employee.model.Persona;
import com.forceclose.Employee.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonaController{

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
}

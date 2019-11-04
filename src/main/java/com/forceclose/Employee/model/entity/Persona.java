package com.forceclose.Employee.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String last_name;

}

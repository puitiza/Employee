package com.forceclose.Employee.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String name;
    @Column
    private String lastName;

}

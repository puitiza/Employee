package com.forceclose.Employee.model.entity.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(nullable = false)
    private boolean activated;

    @Column(nullable = false)
    private Date dateCreated;

    @Column
    private Date dateLastUpdated;

    @Column
    @JsonIgnore
    private String userCreated;

    @Column
    @JsonIgnore
    private String userLastUpdated;
}

package com.forceclose.Employee.model.entity.business;

import com.forceclose.Employee.model.entity.access.HistoricalAccess;
import com.forceclose.Employee.model.entity.access.UserAccess;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    @OneToOne
    @JoinColumn(name="idHistoricalAccess")
    private HistoricalAccess historicalAccess;

}

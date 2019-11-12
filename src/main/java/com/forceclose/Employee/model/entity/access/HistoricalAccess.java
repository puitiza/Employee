package com.forceclose.Employee.model.entity.access;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "HistoricalAccess")
public class HistoricalAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private boolean activated;

    @Column(nullable = false)
    private Date dateCreated;

    @Column
    private Date dateLastUpdated;

    @OneToOne
    @JoinColumn(name = "idUserCreated")
    private UserAccess userCreated;

    @OneToOne
    @JoinColumn(name = "idUserLastUpdated")
    private UserAccess userLastUpdated;

    public HistoricalAccess() {
    }

    public HistoricalAccess(boolean activated, Date dateCreated, UserAccess userCreated) {
        super();
        this.activated = activated;
        this.dateCreated = dateCreated;
        this.userCreated = userCreated;
    }
}

package com.forceclose.Employee.model.entity.access;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forceclose.Employee.model.entity.access.relation.RolePrivilege;
import com.forceclose.Employee.model.entity.access.relation.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "RoleAccess")
public class RoleAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name="idHistoricalAccess")
    private HistoricalAccess historicalAccess;

    @Column
    @JsonIgnore
    private String userCreated;

    @Column
    @JsonIgnore
    private String userLastUpdated;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> userRole;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "role")
    private Set<RolePrivilege> rolePrivilege;

    /* this is the another way for many to many relation
    @ManyToMany(mappedBy = "RoleAccess")
    private Collection<UserAccess> users;*/

    public RoleAccess() {
    }

    public RoleAccess(final String name) {
        super();
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoleAccess role = (RoleAccess) obj;
        return name.equals(role.name);
    }

    @Override
    public String toString() {
        return "RoleAccess [id=" + id + "]" + "\r\n" +
                "[name=" + name + "]";
    }

}

package com.forceclose.Employee.model.entity.access;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forceclose.Employee.model.entity.access.relation.RolePrivilege;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "PrivilegeAccess")
public class PrivilegeAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

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

    @OneToMany(mappedBy = "privilege")
    private Set<RolePrivilege> rolePrivilege;

    public PrivilegeAccess() {
    }

    public PrivilegeAccess(final String name) {
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
        final PrivilegeAccess privilege = (PrivilegeAccess) obj;
        return name.equals(privilege.name);
    }

    @Override
    public String toString() {
        return "PrivilegeAccess [id=" + id + "]" + "\r\n" +
                "[name=" + name + "]";
    }
}

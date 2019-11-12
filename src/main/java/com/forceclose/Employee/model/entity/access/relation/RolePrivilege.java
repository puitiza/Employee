package com.forceclose.Employee.model.entity.access.relation;

import com.forceclose.Employee.model.entity.access.HistoricalAccess;
import com.forceclose.Employee.model.entity.access.PrivilegeAccess;
import com.forceclose.Employee.model.entity.access.RoleAccess;
import com.forceclose.Employee.model.entity.access.UserAccess;
import com.google.common.base.MoreObjects;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "roles_privileges")
public class RolePrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "role_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private RoleAccess role;

    @JoinColumn(name = "privilege_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private PrivilegeAccess privilege;

    // additional fields
    @OneToOne
    @JoinColumn(name="idHistoricalAccess")
    private HistoricalAccess historicalAccess;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RolePrivilege that = (RolePrivilege) o;
        return Objects.equals(role, that.role) &&
                Objects.equals(privilege, that.privilege);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, privilege);
    }

    @Override
    public String toString() {
        return "RolePrivilege_Relation[id=" + id + "]" + "\r\n" +
                "[role=" + role + "]" + "\r\n" +
                "[privilege=" + privilege + "]";
    }
}

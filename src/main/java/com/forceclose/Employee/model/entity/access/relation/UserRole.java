package com.forceclose.Employee.model.entity.access.relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forceclose.Employee.model.entity.access.RoleAccess;
import com.forceclose.Employee.model.entity.access.UserAccess;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "users_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserAccess user;

    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private RoleAccess role;

    // additional fields
    private boolean activated;

    @Column(nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column
    private Date dateLastUpdated;

    @Column
    @JsonIgnore
    private String userCreated;

    @Column
    @JsonIgnore
    private String userLastUpdated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserRole that = (UserRole) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    @Override
    public String toString() {
        return "UserRole_Relation[id=" + id + "]" + "\r\n" +
                "[user=" + user + "]" + "\r\n" +
                "[role=" + role + "]" + "\r\n" +
                "[activated=" + activated + "]" + "\r\n" +
                "[dateCreated=" + dateCreated + "]" + "\r\n" +
                "[dateLastUpdated=" + dateLastUpdated + "]";
    }
}

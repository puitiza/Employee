package com.forceclose.Employee.model.entity.access;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forceclose.Employee.model.entity.access.relation.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "UserAccess")
public class UserAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @OneToOne
    @JoinColumn(name="idHistoricalAccess")
    private HistoricalAccess historicalAccess;

    //  this is a way to call many to many relation but customization the relation
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserRole> userRole;

    /* This is another kind of way call relation Many to Many
     @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
     @JoinTable( name="users_roles",
     joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
     inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
     private Collection<RoleAccess> RoleAccess;*/

    public UserAccess() {
    }

    //  Is necessary to set up hashcode and equals methods to active custom many to many relation
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        final UserAccess role = (UserAccess) obj;
        return username.equals(role.username);
    }

    @Override
    public String toString() {
        return "UserAccess [id=" + id + "]" + "\r\n" +
                "[username=" + username + "]" + "\r\n" +
                "[password=" + password + "]" ;
    }

}
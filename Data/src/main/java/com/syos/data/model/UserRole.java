package main.java.com.syos.data.model;

import jakarta.persistence.*;

@Entity
@Table(name = "UserRole")
public class UserRole {

    @Id
    @Column(name = "UserID")
    private int userID;

    @Id
    @Column(name = "RoleID")
    private int roleID;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "RoleID", insertable = false, updatable = false)
    private Role role;
}

package main.java.com.syos.data.model;

import jakarta.persistence.*;

@Entity
@Table(name = "RolePermission")
public class RolePermission {

    @Id
    @Column(name = "RoleID")
    private int roleID;

    @Id
    @Column(name = "PermissionID")
    private int permissionID;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "RoleID", insertable = false, updatable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "PermissionID", insertable = false, updatable = false)
    private Permissions permission;
}

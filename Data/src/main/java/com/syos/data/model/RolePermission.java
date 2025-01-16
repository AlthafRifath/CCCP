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

    // Getters and Setters
    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(int permissionID) {
        this.permissionID = permissionID;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permissions getPermission() {
        return permission;
    }

    public void setPermission(Permissions permission) {
        this.permission = permission;
    }
}

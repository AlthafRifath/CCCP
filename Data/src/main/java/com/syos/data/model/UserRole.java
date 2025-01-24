package main.java.com.syos.data.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tblUserRole")
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

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Override equals() and hashCode() in all composite key classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole that = (UserRole) o;

        if (userID != that.userID) return false;
        return roleID == that.roleID;
    }

    @Override
    public int hashCode() {
        int result = userID;
        result = 31 * result + roleID;
        return result;
    }
}

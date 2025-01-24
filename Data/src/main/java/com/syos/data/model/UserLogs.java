package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tblUserLogs")
public class UserLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogID")
    private int logID;

    @Column(name = "UserID", nullable = false)
    private int userID;

    @Column(name = "Action", nullable = false)
    private String action;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "LogDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "UserID", insertable = false, updatable = false)
    private User user;

    // Getters and Setters
    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Override equals() and hashCode() in all composite key classes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLogs userLogs = (UserLogs) o;

        return logID == userLogs.logID;
    }

    @Override
    public int hashCode() {
        return logID;
    }
}

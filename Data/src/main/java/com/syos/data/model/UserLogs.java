package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UserLogs")
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
}

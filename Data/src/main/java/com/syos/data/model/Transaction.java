package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int transactionID;

    @Column(name = "BillID", nullable = false)
    private int billID;

    @Column(name = "TransactionType", nullable = false)
    private String transactionType;

    @Column(name = "TransactionDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "BillID", insertable = false, updatable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;
}

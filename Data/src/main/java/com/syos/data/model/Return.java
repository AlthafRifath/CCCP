package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Return")
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReturnID")
    private int returnID;

    @Column(name = "BillID", nullable = false)
    private int billID;

    @Column(name = "ItemCode", nullable = false)
    private String itemCode;

    @Column(name = "BatchCode", nullable = false)
    private String batchCode;

    @Column(name = "Quantity", nullable = false)
    private int quantity;

    @Column(name = "ReturnDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @Column(name = "Reason", nullable = false)
    private String reason;

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
    @JoinColumns({
            @JoinColumn(name = "ItemCode", referencedColumnName = "ItemCode", insertable = false, updatable = false),
            @JoinColumn(name = "BatchCode", referencedColumnName = "BatchCode", insertable = false, updatable = false)
    })
    private Item item;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;
}

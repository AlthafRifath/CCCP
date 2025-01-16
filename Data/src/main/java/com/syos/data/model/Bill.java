package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "Bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillID")
    private int billID;

    @Column(name = "CustomerID")
    private Integer customerID;

    @Column(name = "DiscountID")
    private Integer discountID;

    @Column(name = "SerialNumber", nullable = false)
    private String serialNumber;

    @Column(name = "BillDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDate;

    @Column(name = "TotalAmount", nullable = false)
    private double totalAmount;

    @Column(name = "CashTendered", nullable = false)
    private double cashTendered;

    @Column(name = "Change", nullable = false)
    private double change;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "CustomerID", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "DiscountID", insertable = false, updatable = false)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    @OneToMany(mappedBy = "bill")
    private List<BillItem> billItems;

    @OneToMany(mappedBy = "bill")
    private List<Transaction> transactions;
}

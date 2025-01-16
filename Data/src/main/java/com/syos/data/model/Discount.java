package main.java.com.syos.data.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "Discount")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountID")
    private int discountID;

    @Column(name = "DiscountName", nullable = false)
    private String discountName;

    @Column(name = "DiscountType", nullable = false)
    private String discountType;

    @Column(name = "DiscountValue", nullable = false)
    private double discountValue;

    @Column(name = "ValidFrom", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @Column(name = "ValidTo", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validTo;

    @Column(name = "IsActive", nullable = false)
    private boolean isActive;

    @Column(name = "IsDeleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "UpdatedBy")
    private Integer updatedBy;

    @Column(name = "UpdatedDateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDateTime;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "UpdatedBy", insertable = false, updatable = false)
    private User updatedByUser;

    @OneToMany(mappedBy = "discount")
    private List<Bill> bills;

    @OneToMany(mappedBy = "discount")
    private List<BillItem> billItems;
}

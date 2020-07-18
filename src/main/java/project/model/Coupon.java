package project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Coupons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Long barcode;

    @Column
    private Long usageCounter;

    @Column
    private Long quantity;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_type_id", referencedColumnName = "id")
    private CouponType couponType;

    public enum Status {
        G,  // generated
        N,  // not used
        U,  // used
        E;  // expired
    }
}

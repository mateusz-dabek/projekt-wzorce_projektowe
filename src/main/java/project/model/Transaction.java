package project.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Transactions")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private Double amount;

    @Column
    private Long couponBarcode;

    @Column
    private String couponTypeName;

    @Column
    @Enumerated(EnumType.STRING)
    private Coupon.Status useResult;

    @OneToOne
    @JoinColumn(name = "id")
    private Coupon coupon;

    public static class Builder {
        private final Double amount;

        private Long couponBarcode;
        private String couponTypeName;

        public Builder(Double amount) {
            this.amount = amount;
        }

        public Builder addCoupon(Long barcode, String typeName) {
            couponBarcode = barcode;
            couponTypeName = typeName;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    private Transaction(Builder builder) {
        amount = builder.amount;
        couponBarcode = builder.couponBarcode;
        couponTypeName = builder.couponTypeName;
    }
}

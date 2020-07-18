package project.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CouponTypes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private Long minCode;

    @Column
    private Long maxCode;

    @Column
    private Long remainNumber;

    @Column
    private Long usagePeriod;

    @Column
    private Long useLimitNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "couponType")
    private List<Coupon> coupons;

    public enum Status {
        A, // active
        I; // inactive
    }

    public static class Builder {
        private final String name;
        private final Long minCode;
        private final Long maxCode;

        private Long usagePeriod = 365L;
        private Long useLimitNumber = 1L;

        public Builder(String name, Long minCode, Long maxCode) {
            this.name = name;
            this.minCode = minCode;
            this.maxCode = maxCode;
        }

        public Builder usagePeriod(Long days) {
            usagePeriod = days;
            return this;
        }

        public Builder useLimitNumber(Long useLimit) {
            useLimitNumber = useLimit;
            return this;
        }

        public CouponType build() {
            return new CouponType(this);
        }
    }

    private CouponType(Builder builder) {
        name = builder.name;
        minCode = builder.minCode;
        maxCode = builder.maxCode;
        usagePeriod = builder.usagePeriod;
        useLimitNumber = builder.useLimitNumber;
    }

    @PrePersist
    void prePersist() {
        
    }
}

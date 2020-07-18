package project.repository;

import org.springframework.data.repository.CrudRepository;
import project.model.Coupon;
import project.model.CouponType;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends CrudRepository<Coupon, Long> {
    Optional<Coupon> findByBarcodeAndCouponType(Long barcode, CouponType couponType);
    List<Coupon> getAllByStatus(Coupon.Status status);
}
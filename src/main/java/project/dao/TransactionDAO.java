package project.dao;

import org.springframework.stereotype.Service;
import project.model.Coupon;
import project.model.CouponType;
import project.model.Transaction;
import project.repository.CouponRepository;
import project.repository.CouponTypeRepository;
import project.repository.TransactionRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class TransactionDAO {
    private final TransactionRepository transactionRepository;
    private final CouponRepository couponRepository;
    private final CouponTypeRepository couponTypeRepository;
    private static TransactionDAO INSTANCE;

    public static TransactionDAO getInstance() {
        return INSTANCE;
    }

    @PostConstruct
    private void registerInstance() {
        INSTANCE = this;
    }

    private TransactionDAO(TransactionRepository transactionRepository, CouponRepository couponRepository, CouponTypeRepository couponTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.couponRepository = couponRepository;
        this.couponTypeRepository = couponTypeRepository;
    }

    public void processTransaction(Transaction transaction) {
        if (transaction.getCouponBarcode() != null) {
            Optional<CouponType> couponType = couponTypeRepository.findByName(transaction.getCouponTypeName());
            if (couponType.isPresent()) {
                Optional<Coupon> coupon = couponRepository.findByBarcodeAndCouponType(transaction.getCouponBarcode(), couponType.get());
                if (coupon.isPresent()) {
                    processCoupon(transaction, coupon.get());
                    couponRepository.save(coupon.get());
                } else {
                    setNotUsedCoupon(transaction);
                }
            } else {
                setNotUsedCoupon(transaction);
            }
        } else {
            setNotUsedCoupon(transaction);
        }
        transactionRepository.save(transaction);
    }

    private void processCoupon(Transaction transaction, Coupon coupon) {
        if (!Coupon.Status.E.equals(coupon.getStatus())) {
            transaction.setCouponBarcode(coupon.getBarcode());
            CouponType couponType = coupon.getCouponType();
            transaction.setCouponTypeName(couponType.getName());

            coupon.setUsageCounter(coupon.getUsageCounter() + 1);
            if (coupon.getUsageCounter().equals(coupon.getQuantity())) {
                transaction.setUseResult(Coupon.Status.E);
                coupon.setStatus(Coupon.Status.E);
            } else {
                transaction.setUseResult(Coupon.Status.U);
                coupon.setStatus(Coupon.Status.U);
            }
        } else {
            setNotUsedCoupon(transaction);
        }
    }

    private void setNotUsedCoupon(Transaction transaction) {
        transaction.setCouponBarcode(null);
        transaction.setCouponTypeName(null);
        transaction.setUseResult(Coupon.Status.N);
    }


}


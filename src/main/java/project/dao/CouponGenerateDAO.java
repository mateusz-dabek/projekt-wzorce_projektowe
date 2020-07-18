package project.dao;

import org.springframework.stereotype.Service;
import project.commands.CouponGenerate;
import project.model.Coupon;
import project.model.CouponType;
import project.repository.CouponRepository;
import project.repository.CouponTypeRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CouponGenerateDAO {
    private final CouponTypeRepository couponTypeRepository;
    private final CouponRepository couponRepository;
    private static CouponGenerateDAO INSTANCE;

    public static CouponGenerateDAO getInstance() {
        return INSTANCE;
    }

    @PostConstruct
    private void registerInstance() {
        INSTANCE = this;
    }

    private CouponGenerateDAO(CouponTypeRepository couponTypeRepository, CouponRepository couponRepository) {
        this.couponTypeRepository = couponTypeRepository;
        this.couponRepository = couponRepository;
    }

    public void generateSeries(String typeName, Long quantity) {
        Optional<CouponType> couponTypeOptional = couponTypeRepository.findByName(typeName);
        if (couponTypeOptional.isPresent()) {
            CouponType couponType = couponTypeOptional.get();
            if (couponType.getRemainNumber() >= quantity) {
                List<Coupon> series = new ArrayList<>();
                long startRange = couponType.getMaxCode() - couponType.getRemainNumber();

                for (int i = 0; i < quantity; i++) {
                    Coupon coupon = new Coupon();
                    coupon.setCouponType(couponType);
                    coupon.setBarcode(startRange++);
                    coupon.setQuantity(couponType.getUseLimitNumber());
                    coupon.setStatus(Coupon.Status.G);
                    coupon.setUsageCounter(0L);
                    series.add(coupon);
                }

                couponType.setRemainNumber(couponType.getRemainNumber() - quantity);
                couponRepository.saveAll(series);
                couponType.setCoupons(series);
            } else if (couponType.getRemainNumber() == 0) {
                couponType.setStatus(CouponType.Status.I);
            }
            couponTypeRepository.save(couponType);
        }
    }

    public void burnExpired() {
        List<Coupon> expiredCoupons = couponRepository.getAllByStatus(Coupon.Status.E);
        expiredCoupons.forEach(couponRepository::delete);
        System.out.println("Usunięto " + expiredCoupons.size() + " wygasłych kuponów");
    }
}

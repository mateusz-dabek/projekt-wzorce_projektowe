package project.dao;

import org.springframework.stereotype.Service;
import project.model.CouponType;
import project.repository.CouponTypeRepository;

import javax.annotation.PostConstruct;

@Service
public class CouponTypeDAO {
    private final CouponTypeRepository couponTypeRepository;
    private static CouponTypeDAO INSTANCE;

    public static CouponTypeDAO getInstance() {
        return INSTANCE;
    }

    @PostConstruct
    private void registerInstance() {
        INSTANCE = this;
    }

    private CouponTypeDAO(CouponTypeRepository couponTypeRepository) {
        this.couponTypeRepository = couponTypeRepository;
    }

    public void create(CouponType couponType) {
        if (!couponTypeRepository.existsByName(couponType.getName())) {
            couponType.setStatus(CouponType.Status.A);
            couponType.setRemainNumber(couponType.getMaxCode() - couponType.getMinCode());
            couponTypeRepository.save(couponType);
        }
    }
}

package project.commands;

import project.dao.CouponTypeDAO;
import project.model.CouponType;

public class CouponTypeCreate implements Command {

    private final CouponType couponType;

    public CouponTypeCreate(CouponType couponType) {
        this.couponType = couponType;
    }

    @Override
    public void execute() {
        CouponTypeDAO.getInstance().create(couponType);
    }
}

package project.state;

import project.dao.CouponGenerateDAO;

public class CouponBurn implements CouponState {

    public CouponBurn() {
    }

    @Override
    public void doAction(CouponContext context) {
        context.setState(this);
    }

    @Override
    public void execute() {
        CouponGenerateDAO.getInstance().burnExpired();
    }

}

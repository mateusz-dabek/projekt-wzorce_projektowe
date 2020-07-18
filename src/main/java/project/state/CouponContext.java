package project.state;

public class CouponContext {
    private CouponState state;

    public CouponContext() {
    }

    public CouponState getState() {
        return state;
    }

    public void setState(CouponState state) {
        this.state = state;
    }

}

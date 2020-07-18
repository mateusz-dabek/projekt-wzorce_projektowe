package project.commands;

import project.dao.CouponGenerateDAO;

public class CouponGenerate implements Command {
    private final String typeName;
    private final Long quantity;

    public CouponGenerate(String typeName, Long quantity) {
        this.typeName = typeName;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        CouponGenerateDAO.getInstance().generateSeries(typeName, quantity);
    }
}

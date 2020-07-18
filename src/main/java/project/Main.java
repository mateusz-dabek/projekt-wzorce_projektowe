package project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.commands.CouponGenerate;
import project.commands.CouponTypeCreate;
import project.commands.Invoker;
import project.commands.TransactionProcessor;
import project.model.CouponType;
import project.model.Transaction;
import project.state.CouponBurn;
import project.state.CouponContext;

@SpringBootApplication
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Main.class);
        app.run(args);
    }

    @Override
    public void run(String... args) {
        CouponType couponType = new CouponType
                .Builder("TPC", 1000L, 2000L)
                .useLimitNumber(1L)
                .build();

        CouponTypeCreate couponTypeCreate = new CouponTypeCreate(couponType);

        CouponGenerate couponGenerate = new CouponGenerate(couponType.getName(), 1000L);

        Transaction transaction = new Transaction.Builder(100.0)
                .addCoupon(1000L, "TPC")
                .build();

        TransactionProcessor trnProcess = new TransactionProcessor(transaction);


        Invoker invoker = new Invoker();
        invoker.addCommand(couponTypeCreate);
        invoker.addCommand(couponGenerate);
        invoker.addCommand(trnProcess);

        invoker.executeCommands();


        CouponContext couponContext = new CouponContext();
        CouponBurn burn = new CouponBurn();
        couponContext.setState(burn);
        couponContext.getState().execute();
    }
}






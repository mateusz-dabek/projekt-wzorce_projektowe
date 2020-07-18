package project.repository;

import org.springframework.data.repository.CrudRepository;
import project.model.CouponType;
import project.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}

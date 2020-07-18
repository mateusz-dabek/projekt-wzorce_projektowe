package project.repository;

import org.springframework.data.repository.CrudRepository;
import project.model.CouponType;

import java.util.Optional;

public interface CouponTypeRepository extends CrudRepository<CouponType, Long> {
    Optional<CouponType> findByName(String name);
    boolean existsByName(String name);
}

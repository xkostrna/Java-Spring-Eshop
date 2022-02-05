package sk.stuba.fei.uim.oop.assignment3.cartproduct;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends CrudRepository<CartProduct, Long> {
    Optional<CartProduct> findByProductId(Long aLong);
    CartProduct findCartProductByProductId(Long aLong);
}

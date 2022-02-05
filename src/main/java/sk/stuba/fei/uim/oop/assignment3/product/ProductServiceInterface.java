package sk.stuba.fei.uim.oop.assignment3.product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {
    List<Product> getAll();
    Product createProductByRequest(ProductRequest request);
    Optional<Product> findById(Long id);
    Product findProductById(Long id);
    Product changeProductData(ProductRequest request, Long id);
    void deleteProductById(Long productId);
    Product incrementProductAmountById(AmountRequest request, Long id);
    void saveProduct(Product product);
}

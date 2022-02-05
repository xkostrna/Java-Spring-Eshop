package sk.stuba.fei.uim.oop.assignment3.cartproduct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProductResponse {
    private final Long productId;
    private final Integer amount;

    public CartProductResponse(CartProduct product) {
        this.productId = product.getProductId();
        this.amount = product.getAmount();
    }
}

package sk.stuba.fei.uim.oop.assignment3.product;

import lombok.Getter;

@Getter
public class AmountResponse {

    private final Integer amount;

    public AmountResponse(Product product) {
        this.amount = product.getAmount();
    }
}

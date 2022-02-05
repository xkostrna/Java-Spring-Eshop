package sk.stuba.fei.uim.oop.assignment3.cart;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.cartproduct.CartProductResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CartResponse {

    private final Long id;
    private final Set<CartProductResponse> shoppingList;
    private final boolean payed;

    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.shoppingList = new HashSet<>();
        if (!cart.getShoppingList().isEmpty()) {
            this.shoppingList.addAll(cart.getShoppingList().stream().map(CartProductResponse::new).collect(Collectors.toSet()));
        }
        this.payed = cart.isPayed();
    }
}

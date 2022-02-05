package sk.stuba.fei.uim.oop.assignment3.cart;

import sk.stuba.fei.uim.oop.assignment3.cartproduct.CartProductRequest;

import java.util.Optional;

public interface CartServiceInterface {
    Cart createCart();
    Optional<Cart> findById(Long id);
    Cart findCartById(Long id);
    void deleteCartById(Long id);
    Cart addProductToCart(Long id, CartProductRequest request);
    String payForShoppingCart(Long id);
}

package sk.stuba.fei.uim.oop.assignment3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.cartproduct.CartProductRequest;
import sk.stuba.fei.uim.oop.assignment3.cart.CartResponse;
import sk.stuba.fei.uim.oop.assignment3.cart.CartServiceInterface;
import sk.stuba.fei.uim.oop.assignment3.exceptions.IdNotFoundException;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceInterface service;

    @PostMapping()
    public ResponseEntity<CartResponse> createShoppingCart() {
        return new ResponseEntity<>(new CartResponse(this.service.createCart()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public CartResponse getCartById(@PathVariable("id") Long cartId) {
        return new CartResponse(this.service.findById(cartId).orElseThrow(IdNotFoundException::new));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartById(@PathVariable("id") Long cartId) {
        if(this.service.findById(cartId).isPresent()) {
            this.service.deleteCartById(cartId);
            return new ResponseEntity<>("Deletion successful", HttpStatus.OK);
        }
        else {
            throw new IdNotFoundException();
        }
    }

    @PostMapping("/{id}/add")
    public CartResponse addProductToCart(@PathVariable("id") Long cartId, @RequestBody CartProductRequest cartProduct) {
        if (this.service.findById(cartId).isPresent()) {
            return new CartResponse(this.service.addProductToCart(cartId, cartProduct));
        }
        else {
            throw new IdNotFoundException();
        }
    }

    @GetMapping("/{id}/pay")
    public String payForShoppingCart(@PathVariable("id") Long cartId) {
        if(this.service.findById(cartId).isPresent()) {
            return this.service.payForShoppingCart(cartId);
        }
        else {
            throw new IdNotFoundException();
        }
    }
}

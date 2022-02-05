package sk.stuba.fei.uim.oop.assignment3.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cartproduct.CartProduct;
import sk.stuba.fei.uim.oop.assignment3.cartproduct.CartProductRepository;
import sk.stuba.fei.uim.oop.assignment3.cartproduct.CartProductRequest;
import sk.stuba.fei.uim.oop.assignment3.exceptions.BadRequestException;
import sk.stuba.fei.uim.oop.assignment3.exceptions.IdNotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.Product;
import sk.stuba.fei.uim.oop.assignment3.product.ProductServiceInterface;

import java.util.Optional;

@Service
public class CartService implements CartServiceInterface {

    @Autowired
    private ProductServiceInterface productService;

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Autowired
    public CartService(CartRepository cartRepository, CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
    }

    @Override
    public Cart createCart() {
        return this.cartRepository.save(new Cart());
    }

    @Override
    public Optional<Cart> findById(Long cartId) {
        return this.cartRepository.findById(cartId);
    }

    @Override
    public Cart findCartById(Long cartId) {
        return this.cartRepository.findCartById(cartId);
    }

    @Override
    public void deleteCartById(Long cartId) {
        this.cartRepository.deleteById(cartId);
    }

    @Override
    public Cart addProductToCart(Long cartId, CartProductRequest request) {
        if (this.productService.findById(request.getProductId()).isPresent()) {
            checkIfPayed(cartId);
            checkIfRequestedAmountNotNull(request);
            checkStorageProductAmount(request);
            Cart cart = this.findCartById(cartId);
            if (!checkIfRequestedProductAlreadyInCart(cart, request)) {
                addCartProductToCartByRequest(cart, request);
            }
            return this.cartRepository.save(cart);
        }
        else {
            throw new IdNotFoundException();
        }
    }

    @Override
    public String payForShoppingCart(Long cartId) {
        checkIfPayed(cartId);
        return String.valueOf(getSumOfProductsInCart(cartId));
    }

    private void checkIfPayed(Long cartId) {
        if (this.findCartById(cartId).isPayed()) {
            throw new BadRequestException();
        }
    }

    private void checkStorageProductAmount(CartProductRequest request) {
        Product product = this.productService.findProductById(request.getProductId());
        if (product.getAmount() < request.getAmount()) {
            throw new BadRequestException();
        }
    }

    private void checkIfRequestedAmountNotNull(CartProductRequest request) {
        if (request.getAmount() == null) {
            throw new BadRequestException();
        }
    }

    /*
    i tried to optimize time complexity (of lower method) by using HashSet .contains() method
    but solving problem like this caused error while trying added same product into third cart :(((

    private boolean checkIfCartProductAlreadyInCart(Cart cart, CartProductRequest request) {
        if (this.cartProductRepository.findByProductId(request.getProductId()).isPresent()) {
            CartProduct cartProduct = this.cartProductRepository.findCartProductByProductId(request.getProductId());
            if (cart.getShoppingList().contains(cartProduct)) {
                cartProduct.incrementAmount(request.getAmount());
                this.cartProductRepository.save(cartProduct);
                decrementStorageProductAmount(request);
                return true;
            }
        }
        return false;
    }
     */

    private boolean checkIfRequestedProductAlreadyInCart(Cart cart, CartProductRequest request) {
        for (CartProduct cartProduct : cart.getShoppingList()) {
            if (cartProduct.getProductId().equals(request.getProductId())) {
                cartProduct.incrementAmount(request.getAmount());
                this.cartProductRepository.save(cartProduct);
                decrementStorageProductAmount(request);
                return true;
            }
        }
        return false;
    }

    private void addCartProductToCartByRequest(Cart cart, CartProductRequest request) {
        CartProduct cartProduct = new CartProduct(request);
        this.cartProductRepository.save(cartProduct);
        cart.getShoppingList().add(cartProduct);
        decrementStorageProductAmount(request);
    }

    private void decrementStorageProductAmount(CartProductRequest request) {
        Product product = this.productService.findProductById(request.getProductId());
        product.incrementAmount(-request.getAmount());
        this.productService.saveProduct(product);
    }

    private int getSumOfProductsInCart(Long cartId) {
        Cart cart = this.findCartById(cartId);
        int sumOfProductsInCart = 0;
        for (CartProduct cartProduct : cart.getShoppingList()) {
            Number productPrice = this.productService.findProductById(cartProduct.getProductId()).getPrice();
            sumOfProductsInCart += productPrice.intValue() * cartProduct.getAmount();
        }
        cart.setPayed(true);
        this.cartRepository.save(cart);
        return sumOfProductsInCart;
    }
}

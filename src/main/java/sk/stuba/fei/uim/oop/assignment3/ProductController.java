package sk.stuba.fei.uim.oop.assignment3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exceptions.IdNotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceInterface service;

    @GetMapping()
    public List<ProductResponse> getAllProducts() {
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest request) {
        return new ResponseEntity<>(new ProductResponse(this.service.createProductByRequest(request)), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable("id") Long productId) {
        return new ProductResponse(this.service.findById(productId).orElseThrow(IdNotFoundException::new));
    }

    @PutMapping("/{id}")
    public ProductResponse updateProductById(@PathVariable("id") Long productId, @RequestBody ProductRequest request) {
        if(this.service.findById(productId).isPresent()) {
            return new ProductResponse(this.service.changeProductData(request, productId));
        }
        else {
            throw new IdNotFoundException();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long productId) {
        if(this.service.findById(productId).isPresent()) {
            this.service.deleteProductById(productId);
            return new ResponseEntity<>("Deletion successful", HttpStatus.OK);
        }
        else {
            throw new IdNotFoundException();
        }
    }

    @GetMapping("/{id}/amount")
    public AmountResponse getProductAmountById(@PathVariable("id") Long productId) {
        return new AmountResponse(this.service.findById(productId).orElseThrow(IdNotFoundException::new));
    }

    @PostMapping("/{id}/amount")
    public AmountResponse incerementProductAmountById(@PathVariable("id") Long productId, @RequestBody AmountRequest request) {
        if(this.service.findById(productId).isPresent()) {
            return new AmountResponse(this.service.incrementProductAmountById(request, productId));
        }
        else {
            throw new IdNotFoundException();
        }
    }

}

package sk.stuba.fei.uim.oop.assignment3.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) /*throws IOException*/ {
        this.repository = repository;
        /*
        just uncomment for initialize some products
        try {
            initializeDefaultData();
        }
        catch(IOException e) {
            throw new IOException("Error");
        }
        */
    }

    private void initializeDefaultData() throws IOException {
        FileReader fileReader = new FileReader("src/main/java/sk/stuba/fei/uim/oop/assignment3/product/products.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String s;
        while((s = bufferedReader.readLine()) != null) {
            initializeProductByString(s);
        }
    }

    private void initializeProductByString(String string) {
        String[] tokens = string.split(" ");
        Product newProduct = new Product();
        newProduct.setName(tokens[0].trim());
        newProduct.setDescription(tokens[1].trim());
        newProduct.setAmount(Integer.parseInt(tokens[2].trim()));
        newProduct.setUnit(tokens[3].trim());
        newProduct.setPrice(Integer.parseInt(tokens[4].trim()));
        this.repository.save(newProduct);
    }

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        return this.repository.findProductById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void deleteProductById(Long productId) {
        this.repository.deleteById(productId);
    }

    @Override
    public void saveProduct(Product product) {
        this.repository.save(product);
    }

    @Override
    public Product createProductByRequest(ProductRequest request) {
        Product newProduct = new Product();
        newProduct.setName(request.getName());
        newProduct.setDescription(request.getDescription());
        newProduct.setAmount(request.getAmount());
        newProduct.setUnit(request.getUnit());
        newProduct.setPrice(request.getPrice());
        return this.repository.save(newProduct);
    }

    @Override
    public Product changeProductData(ProductRequest request, Long productId) {
        Product productToBeChanged = this.findProductById(productId);
        if (request.getName() != null) {
            productToBeChanged.setName(request.getName());
        }
        if (request.getDescription() != null) {
            productToBeChanged.setDescription(request.getDescription());
        }
        return this.repository.save(productToBeChanged);
    }

    @Override
    public Product incrementProductAmountById(AmountRequest request, Long id) {
        Product productToBeIncrementedByAmount = this.findProductById(id);
        productToBeIncrementedByAmount.incrementAmount(request.getAmount());
        return this.repository.save(productToBeIncrementedByAmount);
    }
}

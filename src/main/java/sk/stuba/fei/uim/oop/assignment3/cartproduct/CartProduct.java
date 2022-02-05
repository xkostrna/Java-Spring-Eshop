package sk.stuba.fei.uim.oop.assignment3.cartproduct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    private Integer amount;

    public CartProduct(CartProductRequest request) {
        this.setProductId(request.getProductId());
        this.setAmount(request.getAmount());
    }

    public void incrementAmount(Integer amount) {
        this.amount += amount;
    }

}

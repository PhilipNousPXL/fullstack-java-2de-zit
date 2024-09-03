package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    private String owner;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ShoppingCartItem> items;

    public ShoppingCart(String owner) {
        this.owner = owner;
        this.items = new ArrayList<>();
    }

}


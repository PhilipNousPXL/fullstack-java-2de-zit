package be.pxl.services.domain;

import be.pxl.services.util.IntegerListConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "wish_list")
@NoArgsConstructor
public class WishList {

    @Id
    private String owner;

    @Convert(converter = IntegerListConverter.class)
    @Column(name = "products", nullable = false)
    private List<Long> products;

    public WishList(String owner) {
        this.owner = owner;
        this.products = new ArrayList<>();
    }
}

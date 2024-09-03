package be.pxl.services.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    private long id;

    private String name;

    private float price;

    @Enumerated(value = EnumType.STRING)
    private Category category;
}

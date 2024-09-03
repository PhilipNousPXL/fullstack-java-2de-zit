package be.pxl.services.messaging;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductEvent implements Serializable {

    private long id;

    private String name;

    private float price;

    // string of Category enum
    private String category;

    private Action action;
}

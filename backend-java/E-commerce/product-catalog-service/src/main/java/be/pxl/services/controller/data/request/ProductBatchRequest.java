package be.pxl.services.controller.data.request;

import lombok.Data;

@Data
public class ProductBatchRequest {

    private long[] productIds;
}

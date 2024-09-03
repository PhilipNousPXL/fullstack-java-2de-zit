package be.pxl.services.client.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductBatchRequest {
    private long[] productIds;
}

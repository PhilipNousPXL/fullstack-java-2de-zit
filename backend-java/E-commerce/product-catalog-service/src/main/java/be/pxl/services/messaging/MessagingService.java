package be.pxl.services.messaging;

import be.pxl.services.domain.Product;

public interface MessagingService {

    void publishProductUpdate(Product product, Action action, String user);
}

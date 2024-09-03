package be.pxl.services.controller;

import be.pxl.services.controller.data.dto.ShoppingCartDto;
import be.pxl.services.controller.data.request.ShoppingCartRequest;
import be.pxl.services.service.ShoppingCartService;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Slf4j
public class ShoppingCartController {
    private final static String AUTH_HEADER = "Authorization";
    private final ShoppingCartService shoppingCartService;
    private final AuthorizationService authorizationService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, AuthorizationService authorizationService) {
        this.shoppingCartService = shoppingCartService;
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public ResponseEntity<ShoppingCartDto> getShoppingCart(@RequestHeader(value = AUTH_HEADER) String token) {
        log.info("Getting cart");
        log.debug("Getting cart");
        return ResponseEntity.ok(shoppingCartService.getShoppingCart(token));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ShoppingCartDto> modifyShoppingCart(@RequestBody ShoppingCartRequest shoppingCartRequest,
                                                              @RequestHeader(value = AUTH_HEADER) String token,
                                                              @PathVariable long productId) {
        String userName = authorizationService.authorize(token, Role.USER);
        ShoppingCartDto shoppingCartDto = shoppingCartService.updateShoppingCart(userName, productId, shoppingCartRequest);
        return ResponseEntity.ok(shoppingCartDto);
    }

    @PostMapping
    public ResponseEntity<Void> checkout(@RequestHeader(value = AUTH_HEADER) String token) {
        String userName = authorizationService.authorize(token, Role.USER);
        shoppingCartService.checkout(userName);
        return ResponseEntity.accepted().build();
    }
}

package be.pxl.services.controller;

import be.pxl.services.client.data.dto.ProductDto;
import be.pxl.services.service.WishListService;
import be.pxl.services.service.auth.AuthorizationService;
import be.pxl.services.service.auth.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wish-list")
public class WishListController {

    private final static String AUTH_HEADER = "Authorization";
    private final AuthorizationService authorizationService;
    private final WishListService wishListService;

    public WishListController(AuthorizationService authorizationService, WishListService wishListService) {
        this.authorizationService = authorizationService;
        this.wishListService = wishListService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProductToWishList(@RequestHeader(value = AUTH_HEADER) String token,
                                                     @PathVariable long productId) {
        String userName = authorizationService.authorize(token, Role.USER);
        wishListService.addProductToWishList(userName, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromWishList(@RequestHeader(value = AUTH_HEADER) String token,
                                                          @PathVariable long productId) {
        String userName = authorizationService.authorize(token, Role.USER);
        wishListService.removeProductFromWishList(userName, productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ProductDto[]> getWishList(@RequestHeader(value = AUTH_HEADER) String token) {
        String userName = authorizationService.authorize(token, Role.USER);
        ProductDto[] response = wishListService.getWishList(userName);
        return ResponseEntity.ok(response);
    }

}

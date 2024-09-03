import {Component, OnDestroy, OnInit} from '@angular/core';
import {ShoppingCartService} from "../../../services/shopping-cart/shopping-cart.service";
import {ShoppingCart} from "../../../types/shoppingCart";
import {Subscription} from "rxjs";
import {ShoppingCartItem} from "../../../types/shoppingCartItem";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

@Component({
  selector: 'app-shopping-cart-page',
  templateUrl: './shopping-cart-page.component.html',
  styleUrls: ['./shopping-cart-page.component.css']
})
export class ShoppingCartPageComponent implements OnInit, OnDestroy {

  private getShoppingCartSubscription!: Subscription;
  private addSubscription: Subscription | undefined;
  private removeSubscription: Subscription | undefined;
  private checkoutSubscription: Subscription | undefined;


  public shoppingCart: ShoppingCart | undefined;

  public columnsToDisplay = ["name", "category", "price", "quantity", "calc"]

  constructor(private shoppingCartService: ShoppingCartService, private snackBar: MatSnackBar, private router: Router) {
  }

  add(element: ShoppingCartItem) {
    if (this.addSubscription != null) {
      this.addSubscription.unsubscribe();
    }
    this.addSubscription = this.shoppingCartService.addQuantity(element).subscribe((_) => {
      element.quantity++;
      this.snackBar.open("Quantity updated.");
    });
  }

  remove(element: ShoppingCartItem) {
    if (this.removeSubscription != null) {
      this.removeSubscription.unsubscribe();
    }
    this.removeSubscription = this.shoppingCartService.removeQuantity(element).subscribe((_) => {
      if (element.quantity == 1) {
        this.fetchShoppingCart();
      } else {
        element.quantity--;
      }
      this.snackBar.open("Quantity updated.");
    });
  }

  checkout() {
    this.checkoutSubscription = this.shoppingCartService.checkout().subscribe(async (_) => {
      await this.router.navigate(['/succes'])
    })
  }

  ngOnDestroy(): void {
    this.getShoppingCartSubscription.unsubscribe();
    if (this.addSubscription != null) {
      this.addSubscription.unsubscribe();
    }
    if (this.removeSubscription != null) {
      this.removeSubscription.unsubscribe();
    }
    if (this.checkoutSubscription != null) {
      this.checkoutSubscription.unsubscribe();
    }
  }

  fetchShoppingCart() {
    this.getShoppingCartSubscription = this.shoppingCartService.getShoppingCart()
      .subscribe(cart => this.shoppingCart = cart);
  }

  ngOnInit(): void {
    this.fetchShoppingCart();
  }

  get totalPrice() {
    let price = 0;
    for (let item of this.shoppingCart!.items) {
      price += item.product.price * item.quantity;
    }
    return price;
  }

}

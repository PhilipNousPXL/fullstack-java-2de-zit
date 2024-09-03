import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ProductService} from "../../../services/product/product.service";
import {Product} from "../../../types/product";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-wish-list-page',
  templateUrl: './wish-list-page.component.html',
  styleUrls: ['./wish-list-page.component.css']
})
export class WishListPageComponent implements OnInit, OnDestroy {
  private getWishListSubscription!: Subscription;
  public products: Product[] = [];
  columnsToDisplay = ["name", "category", "price", "delete"];

  constructor(private productService: ProductService, private snackBar: MatSnackBar) {
  }

  unWishList(id: number) {
    this.productService.unWishList(id).subscribe((_) => {
      this.products = this.products.filter((p) => p.id != id);
      this.snackBar.open("Product remove from wish list.");
    })
  }

  ngOnDestroy(): void {
    this.getWishListSubscription.unsubscribe();
  }

  ngOnInit(): void {
    this.getWishListSubscription = this.productService.getWishList().subscribe((products) => {
      this.products = products;
    });
  }

}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {ProductService} from "../../../services/product/product.service";
import {Product} from "../../../types/product";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-product-detail-page',
  templateUrl: './product-detail-page.component.html',
  styleUrls: ['./product-detail-page.component.css']
})
export class ProductDetailPageComponent implements OnInit, OnDestroy {

  private routeSubscription!: Subscription
  private getProductByIdSubscription!: Subscription;
  private orderProductSubscription: Subscription | undefined;
  private wishListSubscription: Subscription | undefined;

  public product: Product | undefined;
  public amount: number = 1;
  public isLoading: boolean = false;

  constructor(private route: ActivatedRoute, private productService: ProductService, private snackBar: MatSnackBar) {
  }

  add() {
    this.amount++;
  }

  substract() {
    if (this.amount == 1) {
      return;
    }
    this.amount--;
  }

  get productLabels() {
    return this.product?.labels.filter(label => label.trim() != "" || false);
  }

  wishList() {
    this.wishListSubscription?.unsubscribe();
    this.wishListSubscription = this.productService.wishList(this.product!.id!).subscribe((_) => {
      this.snackBar.open("Product wishlisted!", undefined, {duration: 2000});
    }, (error) => {
      this.snackBar.open("Error. this product is already wishlisted.", undefined, {duration: 2000});
    })
  }

  order() {
    this.isLoading = true;
    this.orderProductSubscription = this.productService
      .orderProduct(this.product!.id!, this.amount).subscribe((_) => {
        this.openSnackBar("Your product has been added to your shopping cart!");
        this.isLoading = false;
      })
  }


  openSnackBar(message: string) {
    this.snackBar.open(message, undefined, {
      duration: 2000,
    });
  }

  ngOnDestroy(): void {
    this.routeSubscription.unsubscribe();
    this.getProductByIdSubscription.unsubscribe();
    if (this.orderProductSubscription != undefined) {
      this.orderProductSubscription.unsubscribe();
    }
    this.wishListSubscription?.unsubscribe();
  }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe((routeParams) => {
      const productId = routeParams["id"];
      this.getProductByIdSubscription = this.productService.getProductById(productId)
        .subscribe((product) => {
          this.product = product;
        });
    });
  }


}

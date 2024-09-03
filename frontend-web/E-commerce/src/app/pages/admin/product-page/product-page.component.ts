import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProductService} from "../../../services/product/product.service";
import {Subscription} from "rxjs";
import {Product} from "../../../types/product";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css']
})
export class ProductPageComponent implements OnInit, OnDestroy {
  private _getAllProductsSubscription!: Subscription;
  private deleteProductSubscription: Subscription | undefined;
  public products: Product[] = [];
  public columnsToDisplay = ["id", "name", "category", "price", "view", "delete"]


  constructor(private productService: ProductService, private router: Router, private snackBar: MatSnackBar) {
  }

  fetchProducts() {
    this._getAllProductsSubscription = this.productService.getAllProducts()
      .subscribe((products) => {
        this.products = products;
      });
  }

  async addNewProduct() {
    await this.router.navigate(['/admin/product'])
  }

  async updateProduct(product: Product) {
    await this.router.navigate(['/admin/product'], {state: product});
  }

  deleteProduct(id: number) {
    this.deleteProductSubscription?.unsubscribe();
    this.deleteProductSubscription = this.productService.deleteProduct(id).subscribe((_) => {
      this.products = this.products.filter((p) => p.id != id)
      this.snackBar.open("Product deleted.", undefined, {duration: 2000});
    });
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  ngOnDestroy(): void {
    this._getAllProductsSubscription.unsubscribe();
    this.deleteProductSubscription?.unsubscribe();
  }

}

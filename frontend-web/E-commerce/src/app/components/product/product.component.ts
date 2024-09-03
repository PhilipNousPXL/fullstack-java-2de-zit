import {Component, Input} from '@angular/core';
import {Product} from "../../types/product";
import {Router} from "@angular/router";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent {
  @Input({required: true})
  product!: Product;

  constructor(private router: Router) {
  }

  async viewProduct() {
    await this.router.navigate(['/user/product', this.product.id])
  }

}

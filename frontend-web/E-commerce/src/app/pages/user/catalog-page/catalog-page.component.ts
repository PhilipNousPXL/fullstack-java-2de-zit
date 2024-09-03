import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProductService} from "../../../services/product/product.service";
import {Subscription} from "rxjs";
import {AuthenticationService} from "../../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {Product} from "../../../types/product";
import {Category} from "../../../types/category";
import {SustainabilityType} from "../../../types/sustainabilityType";

@Component({
  selector: 'app-catalog-page',
  templateUrl: './catalog-page.component.html',
  styleUrls: ['./catalog-page.component.css']
})
export class CatalogPageComponent implements OnInit, OnDestroy {
  private _getAllProductsSubscription!: Subscription;
  private _allProducts: Product[] = [];
  private _filtererdWordProducts: Product[] = [];
  private _filterSustainabilityTypes: string[] = [];

  public products: Product[] = [];
  public filterCategory: Category | undefined;
  public minPriceString: string = "";
  public maxPriceString: string = "";
  public keyWordSearch: string = "";

  constructor(private productService: ProductService,
              private authService: AuthenticationService,
              private router: Router) {
  }

  updateSustainabilityTypeFilter(add: boolean, type: string) {
    if (add) {
      this._filterSustainabilityTypes.push(type);
      return;
    }
    const index = this._filterSustainabilityTypes.indexOf(type);
    if (index > -1) {
      this._filterSustainabilityTypes.splice(index, 1);
    }
  }

  filterName() {
    if (this.keyWordSearch == "" || this.keyWordSearch == undefined) {
      this.products = this._allProducts;
    }
    this.products = this._allProducts.filter((product) => product.name.toLowerCase().trim().includes(this.keyWordSearch));
  }

  filter() {
    let filteredProducts: Product[] = this._allProducts;
    if (this.filterCategory != undefined) {
      filteredProducts = filteredProducts.filter((product) => product.category == this.filterCategory);
    }

    if (this._filterSustainabilityTypes.length != 0) {
      filteredProducts = filteredProducts.filter((product) => {
        let flag: boolean = true;
        let types = product.sustainabilityCriteria.map(e => e.type);
        for (let type of this._filterSustainabilityTypes) {
          if (!types.includes(type)) {
            flag = false;
          }
        }
        return flag;
      });
    }

    const min: number = Number.parseFloat(this.minPriceString);
    const max: number = Number.parseFloat(this.maxPriceString);

    if (!isNaN(min)) {
      filteredProducts = filteredProducts.filter((product) => product.price >= min);
    }

    if (!isNaN(max)) {
      filteredProducts = filteredProducts.filter((product) => product.price <= max);
    }

    this.products = filteredProducts;
  }

  fetchProducts() {
    this._getAllProductsSubscription = this.productService.getAllProducts()
      .subscribe((products) => {
        this._allProducts = products;
        this.products = products;
      });
  }

  ngOnInit(): void {
    this.fetchProducts();
  }

  ngOnDestroy(): void {
    this._getAllProductsSubscription.unsubscribe();
  }

  get categories(): string[] {
    return Object.keys(Category).filter((item) => isNaN(Number(item)));
  }

  get sustainabilityTypes(): string[] {
    return Object.keys(SustainabilityType).filter((item) => isNaN(Number(item)));
  }
}

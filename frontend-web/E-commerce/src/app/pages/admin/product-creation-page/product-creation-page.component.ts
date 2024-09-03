import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {Product} from "../../../types/product";
import {Category} from "../../../types/category";
import {SustainabilityType} from "../../../types/sustainabilityType";
import {ProductService} from "../../../services/product/product.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-product-creation-page',
  templateUrl: './product-creation-page.component.html',
  styleUrls: ['./product-creation-page.component.css']
})
export class ProductCreationPageComponent implements OnInit, OnDestroy {
  productForm: FormGroup = null!
  readonly scores = [1, 2, 3, 4, 5]
  isUpdate = false;
  product!: Product;
  isLoading: boolean = false;

  private addProductSubscription: Subscription | undefined;

  constructor(private productService: ProductService, private router: Router) {
    const existingProduct = this.router.getCurrentNavigation()?.extras.state;
    if (existingProduct != null) {
      this.product = existingProduct as Product;
      this.isUpdate = true;
      return;
    }
    this.product = {
      name: "",
      price: 0,
      category: Category.DRINKS,
      description: "",
      labels: [],
      sustainabilityCriteria: [{score: 3, type: SustainabilityType[SustainabilityType.SUSTAINABLE_MATERIALS]}],
    }
  }

  ngOnInit(): void {
    this.productForm = new FormGroup({
      name: new FormControl(this.product.name, [Validators.required, Validators.minLength(5)]),
      price: new FormControl(this.product.price, [Validators.required, Validators.pattern('^([0-9]+([.][0-9]*)?|[.][0-9]+)$'), Validators.min(0.01)]),
      description: new FormControl(this.product.description, []),
      category: new FormControl(Category[this.product.category], []),
      labels: new FormArray([]),
      sustainabilityCriteria: new FormArray([]),
    });
    this.product.labels.forEach((label) => this.addLabelInput(label));
    this.product.sustainabilityCriteria.forEach((criteria) => {
      this.addSustainabilityCriteriaInput(criteria.type, criteria.score);
    })
  }

  onSubmit() {
    this.isLoading = true;
    this.product = {
      id: this.isUpdate ? this.product.id : undefined,
      name: this.productForm.get('name')?.value,
      price: this.productForm.get('price')?.value,
      description: this.productForm.get('description')?.value,
      category: this.productForm.get('category')?.value,
      labels: this.productForm.get('labels')?.value.map((label: string) => label),
      sustainabilityCriteria: this.productForm.get('sustainabilityCriteria')?.value
        .map((criteria: any) => {
          return {
            type: criteria.sustainabilityCriteriaType,
            score: criteria.score
          }
        })
    };

    if (this.isUpdate) {
      this.addProductSubscription = this.productService.updateExistingProduct(this.product.id!, this.product).subscribe(async (_) => {
        await this.router.navigate(['/admin']);
      });
    } else {
      this.addProductSubscription = this.productService.addProduct(this.product).subscribe(async (_) => {
        await this.router.navigate(['/admin']);
      });
    }
  }

  addSustainabilityCriteriaInput(type: string | undefined, score: number | undefined) {
    const form = new FormGroup({
      sustainabilityCriteriaType: new FormControl(type ?? SustainabilityType[SustainabilityType.FAIR_TRADE], []),
      score: new FormControl(score ?? 3, [])
    })
    this.sustainabilityCriteriaInput.push(form);
  }

  deleteSustainabilityCriteriaInput(index: number) {
    this.sustainabilityCriteriaInput.removeAt(index);
  }

  addLabelInput(text: string | undefined) {
    const form = new FormControl(text ?? '', []);
    this.labelInput.push(form);
  }

  deleteLabelInput(index: number) {
    this.labelInput.removeAt(index);
  }

  get sustainabilityCriteriaInput() {
    return this.productForm.controls["sustainabilityCriteria"] as FormArray;
  }

  get labelInput() {
    return this.productForm.controls["labels"] as FormArray;
  }

  get sustainabilityCriteriaTypes(): string[] {
    return Object.keys(SustainabilityType).filter((item) => {
      return isNaN(Number(item));
    });
  }

  get categories(): string [] {
    return Object.keys(Category).filter((item) => {
      return isNaN(Number(item));
    });
  }

  ngOnDestroy(): void {
    this.addProductSubscription?.unsubscribe();
  }


}

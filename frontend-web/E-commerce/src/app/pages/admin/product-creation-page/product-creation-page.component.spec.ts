import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProductCreationPageComponent} from './product-creation-page.component';
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {ProductService} from "../../../services/product/product.service";
import {Router} from "@angular/router";

describe('ProductCreationPageComponent', () => {
  let component: ProductCreationPageComponent;
  let fixture: ComponentFixture<ProductCreationPageComponent>;
  let productServiceMock: ProductService;
  let routerMock: Router;

  beforeEach(() => {
    productServiceMock = jasmine.createSpyObj(["addProduct"]);
    routerMock = jasmine.createSpyObj(["navigate", "getCurrentNavigation"])
    TestBed.configureTestingModule({
      declarations: [ProductCreationPageComponent],
      schemas: [NO_ERRORS_SCHEMA],
      providers: [
        {provide: ProductService, useValue: productServiceMock},
        {provide: Router, useValue: routerMock}
      ]
    });
    fixture = TestBed.createComponent(ProductCreationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('initialize the form with default values', () => {
    fixture.detectChanges();

    expect(fixture.componentInstance.productForm).toBeTruthy();
    expect(fixture.componentInstance.isUpdate).toEqual(false);
    expect(fixture.componentInstance.isLoading).toEqual(false);
    expect(fixture.componentInstance.productForm.get('name')?.value).toEqual("");
  });

  it('submit should call correct add method', () => {
    fixture.detectChanges();

    fixture.componentInstance.onSubmit();


  });

});

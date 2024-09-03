import {TestBed} from '@angular/core/testing';

import {ProductService} from './product.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Product} from "../../types/product";
import {Category} from "../../types/category";
import {SustainabilityType} from "../../types/sustainabilityType";
import {environment} from "../../../environments/environment";

describe('ProductService', () => {
  let service: ProductService;
  let httpController: HttpTestingController
  let validProduct: Product;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ProductService]
    });
    httpController = TestBed.inject(HttpTestingController);
    service = TestBed.inject(ProductService);
    validProduct = {
      name: "test product",
      category: Category.DRINKS,
      description: "A desc",
      price: 30.99,
      labels: ["One"],
      sustainabilityCriteria: [{score: 3, type: SustainabilityType[SustainabilityType.SUSTAINABLE_MATERIALS]}]
    }
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('add product test', () => {
    service.addProduct(validProduct).subscribe((addedProduct) => {
      expect(addedProduct.name).toEqual(validProduct.name);
    })

    const request = httpController.expectOne(`${environment.backendUrl}/product`)

    request.flush(validProduct)
    httpController.verify();
  });

  it('updateExistingProduct test', () => {
    validProduct.id = 3;
    service.updateExistingProduct(validProduct.id, validProduct).subscribe((newProduct) => {
      expect(newProduct.name).toEqual(validProduct.name);
    })

    const request = httpController.expectOne(`${environment.backendUrl}/product/3`)

    request.flush(validProduct)
    httpController.verify();
  });

  it('deleteProduct test', () => {
    const productId = 3;
    service.deleteProduct(productId).subscribe((_) => {
      expect(_).toBeFalsy()
    })

    const request = httpController.expectOne(`${environment.backendUrl}/product/3`)

    request.flush(null)
    httpController.verify();
  });

  it('get all products test', () => {
    const products: Product[] = [
      validProduct,
      {
        name: "second",
        category: Category.CLOTHES,
        description: "A desc",
        price: 20.99,
        labels: [],
        sustainabilityCriteria: [{score: 3, type: SustainabilityType[SustainabilityType.SUSTAINABLE_MATERIALS]}]
      }
    ]
    service.getAllProducts().subscribe((data) => {
      expect(data.length).toEqual(products.length)
    })

    const request = httpController.expectOne(`${environment.backendUrl}/product`)

    request.flush(products)
    httpController.verify();
  });

  it('get product by id test', () => {
    validProduct.id = 3;
    service.getProductById(3).subscribe((product) => {
      expect(product.id).toEqual(3);
    })

    const request = httpController.expectOne(`${environment.backendUrl}/product/3`)

    request.flush(validProduct)
    httpController.verify();
  });

  it('order product test', () => {
    service.orderProduct(9, 3).subscribe((data) => {
      expect(data).toBeFalsy()
    })

    const request = httpController.expectOne(`${environment.backendUrl}/cart/9`)

    request.flush(null)
    httpController.verify();
  });

  it('wish list test', () => {
    const id = 3
    service.wishList(id).subscribe((data) => {
      expect(data).toBeFalsy()
    })

    const request = httpController.expectOne(`${environment.backendUrl}/wish-list/${id}`)

    request.flush(null)
    httpController.verify();
  });

  it('unwish list test', () => {
    const id = 3
    service.unWishList(id).subscribe((data) => {
      expect(data).toBeFalsy()
    })

    const request = httpController.expectOne(`${environment.backendUrl}/wish-list/${id}`)

    request.flush(null)
    httpController.verify();
  });

  it('get wish list test', () => {
    service.getWishList().subscribe((data) => {
      expect(data).toBeFalsy()
    })

    const request = httpController.expectOne(`${environment.backendUrl}/wish-list`)

    request.flush([])
    httpController.verify();
  });


});

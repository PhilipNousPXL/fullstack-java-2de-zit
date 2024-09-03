import {ProductCreationPageComponent} from "./product-creation-page.component";
import {of} from "rxjs";
import {Product} from "../../../types/product";
import {Category} from "../../../types/category";
import {SustainabilityType} from "../../../types/sustainabilityType";

describe("prod creating page unit tests", () => {
  let page: ProductCreationPageComponent;
  let productServiceMock: any;
  let routerMock: any;
  let product: Product;

  beforeEach(() => {
    productServiceMock = jasmine.createSpyObj(["addProduct"]);
    routerMock = jasmine.createSpyObj(["navigate", "getCurrentNavigation"])
    page = new ProductCreationPageComponent(productServiceMock, routerMock);
    product = {
      name: "test product",
      category: Category.DRINKS,
      description: "A desc",
      price: 30.99,
      labels: ["One"],
      sustainabilityCriteria: [{score: 3, type: SustainabilityType[SustainabilityType.SUSTAINABLE_MATERIALS]}]
    }
  })

  it('should initialize with default values', () => {
    expect(page).toBeTruthy()
    expect(page.isUpdate).toEqual(false);
    expect(page.product.name).toEqual("");
  });

  it('should initialize with values from the router if an object if passed', () => {
    routerMock.getCurrentNavigation.and.returnValue(of({extras: {state: product}}))
    let page = new ProductCreationPageComponent(productServiceMock, routerMock);
    expect(page).toBeTruthy();
    expect(page.isUpdate).toEqual(true);
    expect(page.product.name).toEqual(product.name);
    expect(page.product.price).toEqual(product.price);
    expect(page.product.category).toEqual(product.category);
    expect(page.product.labels.length).toEqual(product.labels.length);
  });
})

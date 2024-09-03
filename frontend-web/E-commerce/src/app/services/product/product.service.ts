import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {AuthenticationService} from "../authentication/authentication.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Product} from "../../types/product";
import {environment} from "../../../environments/environment";
import standardHeaders from "../header";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient, private authService: AuthenticationService) {
  }

  addProduct(product: Product) {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.post<Product>(`${environment.backendUrl}/product`, product, {headers: headers})
  }

  updateExistingProduct(id: number, product: Product) {
    let headers = standardHeaders(this.authService.authToken!);

    product.id = undefined;

    return this.http.put<Product>(`${environment.backendUrl}/product/${id}`, product, {headers: headers})
  }

  deleteProduct(id: number) {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.delete(`${environment.backendUrl}/product/${id}`, {headers: headers});
  }

  getAllProducts(): Observable<Product[]> {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.get<Product[]>(`${environment.backendUrl}/product`, {headers: headers})
      .pipe(
        map(data => data as Product[]),
        catchError(this.handleError)
      );
  }

  getProductById(id: number): Observable<Product> {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.get<Product>(`${environment.backendUrl}/product/${id}`, {headers: headers})
      .pipe(
        map(data => data as Product),
        catchError(this.handleError)
      );
  }

  orderProduct(productId: number, quantity: number): Observable<void> {
    let headers = standardHeaders(this.authService.authToken!);


    const body = {
      operation: "ADD",
      quantity
    }

    return this.http.put<void>(`${environment.backendUrl}/cart/${productId}`, body, {headers: headers});
  }

  wishList(id: number) {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.post<void>(`${environment.backendUrl}/wish-list/${id}`, undefined, {headers: headers})
  }

  unWishList(id: number) {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.delete<void>(`${environment.backendUrl}/wish-list/${id}`, {headers: headers})
  }

  getWishList() {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.get<Product[]>(`${environment.backendUrl}/wish-list`, {headers: headers})
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => new Error("idk"));
  }
}

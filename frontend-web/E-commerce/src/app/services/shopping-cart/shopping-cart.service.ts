import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ShoppingCart} from "../../types/shoppingCart";
import {map, Observable} from "rxjs";
import {AuthenticationService} from "../authentication/authentication.service";
import standardHeaders from "../header";
import {ShoppingCartItem} from "../../types/shoppingCartItem";

@Injectable({
  providedIn: 'root'
})
export class ShoppingCartService {

  constructor(private http: HttpClient, private authService: AuthenticationService) {
  }

  getShoppingCart(): Observable<ShoppingCart> {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.get<ShoppingCart>(`${environment.backendUrl}/cart`, {headers})
      .pipe(map(data => data as ShoppingCart));
  }

  checkout() {
    let headers = standardHeaders(this.authService.authToken!);

    return this.http.post<void>(`${environment.backendUrl}/cart`, undefined, {headers});
  }

  addQuantity(item: ShoppingCartItem) {
    let headers = standardHeaders(this.authService.authToken!);

    const body = {
      operation: "ADD",
      quantity: 1
    }
    return this.http.put<void>(`${environment.backendUrl}/cart/${item.product.id}`, body, {headers: headers});
  }

  removeQuantity(item: ShoppingCartItem) {
    let headers = standardHeaders(this.authService.authToken!);

    const body = {
      operation: "REMOVE",
      quantity: 1
    }
    return this.http.put<void>(`${environment.backendUrl}/cart/${item.product.id}`, body, {headers: headers});
  }
}

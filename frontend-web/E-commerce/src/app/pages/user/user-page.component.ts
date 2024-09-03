import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent {
  constructor(private router: Router, private authService: AuthenticationService) {
  }

  async showShoppingCart() {
    await this.router.navigate(["/user/shopping-cart"]);
  }

  async logOut() {
    this.authService.logOut();
    await this.router.navigate([""]);
  }
}

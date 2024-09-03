import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  username: string = "";

  password: string = "";

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  async onSubmit(): Promise<void> {
    this.authService.logIn(this.username, this.password);
    if (this.authService.isAdmin()) {
      await this.router.navigate(["admin"]);
      return;
    }
    await this.router.navigate(["user/catalog"]);
  }
}

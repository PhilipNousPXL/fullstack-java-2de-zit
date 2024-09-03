import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent {

  constructor(private router: Router, private authService: AuthenticationService) {
  }

  async logOut() {
    this.authService.logOut();
    await this.router.navigate([""]);
  }
}
